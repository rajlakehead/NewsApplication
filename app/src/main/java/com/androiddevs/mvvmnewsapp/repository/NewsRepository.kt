package com.androiddevs.mvvmnewsapp.repository

import com.androiddevs.mvvmnewsapp.api.RetrofitInstance
import com.androiddevs.mvvmnewsapp.db.ArticleDatabase
import com.androiddevs.mvvmnewsapp.models.Article

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun getSportNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getSportNews(countryCode, pageNumber)

    suspend fun getScienceNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getScienceNews(countryCode, pageNumber)

    suspend fun getEntertainmentNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getEntertainmentNews(countryCode, pageNumber)

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)
}