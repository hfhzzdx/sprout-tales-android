# Sprout Tales (儿童故事 APK)

离线儿童故事应用（3-8 岁），支持：
- 内置 ≥320 篇离线模板故事（构建时生成）
- 导入 .txt / .epub
- 系统 TTS 语音朗读（离线包可用）
- 后台播放、通知/锁屏控制、定时停止
- 绘本插画轮播（轻量占位，后续可拓展）
- 无网络权限、无账号/打点

设备支持：Android 8.0+（minSdk 26），targetSdk 35（覆盖 Android 15/HyperOS 3）。

---

快速开始
1) 你在 GitHub 创建仓库：sprout-tales-android（或你自定义名字）
2) 将本仓库内容推送到你的仓库
3) 打开 GitHub → Actions → 运行 Android CI 工作流，即可产出 APK

可选签名（Release）
- 在仓库 Settings → Secrets and variables → Actions 中新增：
  - ANDROID_KEYSTORE_BASE64（base64 的 .keystore 内容）
  - ANDROID_KEYSTORE_PASSWORD
  - ANDROID_KEY_ALIAS
  - ANDROID_KEY_PASSWORD
- 工作流会自动使用签名信息构建已签名的 release APK。若未提供，将输出未签名的 release APK（可直接安装）。

安装与部署
- 方式 A（手机下载安装）：从 Actions 的 Artifacts/Release 下载 app-release.apk，允许安装未知来源，直接安装。
- 方式 B（ADB）：adb install -r app-release.apk
- 首次建议：在系统设置中为 TTS 引擎下载中文离线语音包；打开应用家长区设置语速/音调；从家长区导入 txt/epub。

项目结构
- app：Android 应用（Compose UI、导航、家长区、播放器入口）
- core-model：数据模型（Story/Chapter/Progress 等）
- data-store：Room 持久化（索引与进度）
- tts-engine：TTS 播放控制（段落切分、播放队列）
- media-playback：前台服务 + MediaSession（锁屏/耳机控制/定时器）
- importers：txt 导入器 + epub 导入器（轻量解析）
- picturebook：插画映射与轮播（占位，可拓展）
- tools/generator：构建时生成 ≥320 篇故事（pack.json 写入 app 资产）
- .github/workflows/android.yml：GitHub Actions 构建

注意
- 本仓库不申请 INTERNET 权限；所有内容皆本地离线。
- EPUB 解析器为轻量实现，可处理常见 xhtml 结构；复杂 EPUB 若解析失败，可先用桌面工具转简化版再导入。
- 代码以清晰稳定为先，后续可逐步增强（多音色、角色语速、插画包扩展等）。
