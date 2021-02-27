package com.androiddevs.mvvmnewsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.models.Article
import com.androiddevs.mvvmnewsapp.models.NewsResponse
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import com.androiddevs.mvvmnewsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: NewsResponse? = null

    val scienceNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var scienceNewsPage = 1
    var scienceNewsResponse: NewsResponse? = null

    val entertainmentNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var entertainmentNewsPage = 1
    var entertainmentNewsResponse: NewsResponse? = null

    val sportNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var sportNewsPage = 1
    var sportNewsResponse: NewsResponse? = null



    init {
        getBreakingNews("us")
        getSportNews("us")
        getEntertainmentNews("us")
        getScienceNews("us")
    }


    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }
    fun getScienceNews(countryCode: String) = viewModelScope.launch {
        scienceNews.postValue(Resource.Loading())
        val response = newsRepository.getScienceNews(countryCode, scienceNewsPage)
        scienceNews.postValue(handleScienceNewsResponse(response))
    }
    fun getEntertainmentNews(countryCode: String) = viewModelScope.launch {
        entertainmentNews.postValue(Resource.Loading())
        val response = newsRepository.getEntertainmentNews(countryCode, entertainmentNewsPage)
        entertainmentNews.postValue(handleEntertainmentNewsResponse(response))
    }
    fun getSportNews(countryCode: String) = viewModelScope.launch {
        sportNews.postValue(Resource.Loading())
        val response = newsRepository.getSportNews(countryCode, sportNewsPage)
        sportNews.postValue(handleSportNewsResponse(response))
    }


    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                breakingNewsPage++
                if(breakingNewsResponse == null) {
                    breakingNewsResponse = resultResponse
                } else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handleScienceNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                scienceNewsPage++
                if(scienceNewsResponse == null) {
                    scienceNewsResponse = resultResponse
                } else {
                    val oldArticles = scienceNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(scienceNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handleEntertainmentNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                entertainmentNewsPage++
                if(entertainmentNewsResponse == null) {
                    entertainmentNewsResponse = resultResponse
                } else {
                    val oldArticles = entertainmentNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(entertainmentNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handleSportNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                sportNewsPage++
                if(sportNewsResponse == null) {
                    sportNewsResponse = resultResponse
                } else {
                    val oldArticles = sportNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(sportNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }
}












