# 部署与签名指南 (Deploy & Sign)

适用范围
- Android 8.0+（minSdk 26），targetSdk 35
- 本地构建/安装、离线签名、CI 构建与发版

准备环境
- JDK 17（Temurin/Adoptium 推荐）
- Android SDK + Build-Tools（建议 35.0.0 及以上，包含 zipalign 与 apksigner）
- 平台工具（adb）用于调试安装

目录与产物位置
- Debug APK（已用调试证书签名，可直接安装）：app/build/outputs/apk/debug/app-debug.apk
- Release APK（未签名）：app/build/outputs/apk/release/app-release-unsigned.apk

一、快速本地构建
- 先生成离线故事包（≥320 篇）：
  ./gradlew :tools:generator:run
- 构建 Debug（推荐用于真机安装体验）：
  ./gradlew assembleDebug
  产物：app/build/outputs/apk/debug/app-debug.apk（已签名）
- 构建 Release（未签名分发包）：
  ./gradlew assembleRelease
  产物：app/build/outputs/apk/release/app-release-unsigned.apk（需签名后才能安装）

二、为什么安装时报“解析软件包时出现问题 (33)”
- 原因：尝试安装未签名的 app-release-unsigned.apk。Android 7.0 起必须签名。
- 解决：
  1) 安装 app-debug.apk（已签名，最快）
  2) 或者对 release-unsigned 手动签名（见下文）

三、手动签名 release APK（本地）
1) 生成签名证书（JKS/PKCS12 均可，示例用 .jks）
   keytool -genkeypair -v \
     -keystore ~/my-release-key.jks \
     -storetype JKS \
     -keyalg RSA -keysize 2048 -validity 3650 \
     -alias mykey
   - 记住以下信息：
     - keystore 路径：~/my-release-key.jks
     - storePassword / keyPassword
     - keyAlias（上例为 mykey）

2) 对未签名 APK 对齐（zipalign）并签名（apksigner）
   macOS/Linux：
     export ANDROID_SDK_ROOT="$HOME/Android/Sdk"  # 按实际路径
     export BT="$ANDROID_SDK_ROOT/build-tools/35.0.0"
     "$BT/zipalign" -p -f 4 \
       app/build/outputs/apk/release/app-release-unsigned.apk \
       app-release-aligned.apk
     "$BT/apksigner" sign \
       --ks ~/my-release-key.jks \
       --ks-key-alias mykey \
       --out app-release-signed.apk \
       app-release-aligned.apk
     "$BT/apksigner" verify --print-certs app-release-signed.apk
     adb install -r app-release-signed.apk

   Windows PowerShell：
     $env:ANDROID_SDK_ROOT="$env:LOCALAPPDATA\Android\Sdk"  # 按实际路径
     $bt="$env:ANDROID_SDK_ROOT\build-tools\35.0.0"
     & "$bt\zipalign.exe" -p -f 4 `
       app\build\outputs\apk\release\app-release-unsigned.apk `
       app-release-aligned.apk
     & "$bt\apksigner.exe" sign `
       --ks "$HOME\my-release-key.jks" `
       --ks-key-alias mykey `
       --out app-release-signed.apk `
       app-release-aligned.apk
     & "$bt\apksigner.exe" verify --print-certs app-release-signed.apk
     adb install -r app-release-signed.apk

提示：AGP 通常已 zipalign，但保守起见仍建议先 zipalign 再签名。若找不到 zipalign/apksigner，请确认 Build-Tools 版本路径无误。

四、用调试证书给 release-unsigned 临时签名（仅自测）
- Android 默认提供 debug.keystore（alias: androiddebugkey，密码均为 android）：
  macOS/Linux：~/.android/debug.keystore
  Windows：%USERPROFILE%\.android\debug.keystore
- 签名命令同上，只需替换 --ks 与 alias：
  apksigner sign --ks ~/.android/debug.keystore \
    --ks-key-alias androiddebugkey \
    --ks-pass pass:android --key-pass pass:android \
    --out app-release-signed-debugkey.apk app-release-aligned.apk
- 注意：调试证书仅用于线下安装体验，不用于对外分发。

五、在 Gradle 中启用本地 Release 自动签名（可选）
- app/build.gradle.kts 已内置条件签名逻辑：仅当以下属性全部存在时才创建 signingConfigs 并用于 release 构建：
  - storeFile, storePassword, keyAlias, keyPassword
- 你可以在 gradle.properties（项目根或全局 ~/.gradle/gradle.properties）里设置：
  storeFile=/absolute/path/to/my-release-key.jks
  storePassword=xxxx
  keyAlias=mykey
  keyPassword=xxxx
- 然后直接：
  ./gradlew assembleRelease
  产物将是已签名的 release APK。

六、CI 构建与发布（GitHub Actions）
- Push 到 main：自动构建 Debug+Release，上传 Artifacts（不发版）
- 发版两种方式：
  1) 推送 tag（v1.x.y）
  2) 提交信息包含 [release] 的 push 到 main（构建成功后自动生成下一个 v* 标签）
- Release 附件：
  - app-debug.apk（已签名，可直接安装）
  - app-release-unsigned.apk（未签名）
- 工作流文件：.github/workflows/android.yml（已设置 contents: write 权限、自动算 tag、条件发版）

七、常见问题
- 安装失败“解析软件包时出现问题 (33)”：未签名，改装 app-debug.apk 或对 release 先签名
- 找不到 zipalign/apksigner：安装对应 Build-Tools，确认路径；或使用 Android Studio 自带工具
- Story 包为空：先运行 :tools:generator:run 生成 app/src/main/assets/stories/pack.json；若缺失则会回退到 pack.sample.json（仅演示数量）

如需进一步自动化（例如 CI 用你自己的证书签名 release 包），可以在 GitHub Secrets 中放置 keystore/base64 与密码变量，并在工作流里解码写入临时文件后跑签名步骤。当前仓库默认保持“无签名 release”，以便云端构建无需任何 secrets。
