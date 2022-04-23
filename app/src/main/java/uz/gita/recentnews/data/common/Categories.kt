package uz.gita.recentnews.data.common

import uz.gita.recentnews.R


object Categories {
    fun getAllCategories(): List<SnapData> {
        return listOf(
            SnapData("World", "world", R.drawable.news_world),
            SnapData("Technology", "technology", R.drawable.news_technology),
            SnapData("Startup", "startup", R.drawable.news_startup),
            SnapData("Entertainment", "entertainment", R.drawable.news_entertainment),
            SnapData("Science", "science", R.drawable.news_science),
            SnapData("Business", "business", R.drawable.news_business),
            SnapData("National", "national", R.drawable.news_national),
            SnapData("Automobile", "automobile", R.drawable.news_automobile),
            SnapData("Politics", "politics", R.drawable.news_politics)
        )
    }
}