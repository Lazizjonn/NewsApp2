package uz.gita.recentnews.data.model

object Categories {

    fun getAllCategories(): List<String> {
        val categories = ArrayList<String>()

        categories.add("all")
        categories.add("national")
        categories.add("business")
        categories.add("sports")
        categories.add("world")
        categories.add("politics")
        categories.add("technology")
        categories.add("startup")
        categories.add("entertainment")
        categories.add("science")
        categories.add("automobile")

        return categories
    }

}