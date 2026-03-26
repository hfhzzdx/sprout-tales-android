package app.sprouttales.tools.generator

import app.sprouttales.model.Story
import app.sprouttales.model.StoryPack
import kotlin.random.Random

object StoryGenerator {
    private val themes = listOf(
        "友谊","诚信","勇气","分享","环保","好奇心","感恩","坚持","共情","安全","多元","科学"
    )
    private val ages = listOf("3-5","6-8")

    private val characters = listOf("小熊","小鹿","小猫","小狗","小兔","星芽","豆豆","米粒","果果","乐乐")
    private val places = listOf("森林","校园","海边","小镇","花园","山谷","星空下","图书馆")

    fun generate(count: Int = 340): StoryPack {
        val rng = Random(20260325)
        val stories = buildList {
            repeat(count) { idx ->
                val theme = themes[idx % themes.size]
                val age = ages[idx % ages.size]
                val title = "${characters[idx % characters.size]}的${theme}冒险"
                val place = places[(idx / 3) % places.size]
                val paragraphs = if (age == "3-5") shortStory(theme, place, rng) else longStory(theme, place, rng)
                add(
                    Story(
                        id = "builtin-$idx",
                        title = title,
                        ageRange = age,
                        theme = theme,
                        paragraphs = paragraphs,
                        pictureHints = listOf(place),
                        durationEstimateSec = paragraphs.sumOf { it.length } / 8
                    )
                )
            }
        }
        return StoryPack(stories)
    }

    private fun shortStory(theme: String, place: String, rng: Random): List<String> {
        val s1 = "在$place里，阳光像蜂蜜一样洒下来。"
        val s2 = "星芽和豆豆决定学会${theme}。"
        val s3 = "他们把叶子做成小船，互相帮助，让小船不翻。"
        val s4 = "风轻轻地吹，心也变得更勇敢。"
        val s5 = "原来${theme}就是把温暖分给别人。"
        return listOf(s1, s2, s3, s4, s5)
    }

    private fun longStory(theme: String, place: String, rng: Random): List<String> {
        val p1 = "清晨的$place，露珠一颗一颗像会唱歌。"
        val p2 = "乐乐遇到一个难题：要不要把漂亮的贝壳和同学分享？"
        val p3 = "他想到老师说过：${theme}像灯，会从一个人点到另一个人。"
        val p4 = "他鼓起勇气，把贝壳放到班级的‘自然角’，大家一起研究它的纹路。"
        val p5 = "放学时，乐乐更开心了，因为心里那盏灯更亮了。"
        return listOf(p1, p2, p3, p4, p5)
    }
}
