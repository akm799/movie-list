package com.akm.test.movielist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.akm.test.movielist.domain.model.MovieRow
import com.akm.test.movielist.view.MovieView
import com.akm.test.movielist.view.error.buildErrorMessage
import com.akm.test.movielist.view.observer.MovieObserver
import com.akm.test.movielist.view.observer.MovieObserverImpl
import com.akm.test.movielist.view.processor.MovieProcessor
import com.akm.test.movielist.view.processor.MovieProcessorImpl
import com.akm.test.movielist.view.ui.list.adapter.MovieAdapter
import com.akm.test.movielist.view.ui.list.scroll.ListEndDetector
import com.akm.test.movielist.view.ui.list.scroll.ListEndListener
import com.akm.test.movielist.view.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Activity implementing our movie view. In a multi screen application with a more complex flow a
 * fragment might be better suited for that purpose. However, in our simple case the activity will
 * perform that task for the sake of simplicity.
 */
class MainActivity : AppCompatActivity(), MovieView, ListEndListener {
    private val viewModel: MovieViewModel by viewModel()
    private val processor: MovieProcessor = MovieProcessorImpl()

    private lateinit var observer: MovieObserver
    private lateinit var listEndDetector: ListEndDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observer = MovieObserverImpl(this, viewModel, processor)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        listEndDetector = ListEndDetector(movieList)
        movieList.layoutManager = LinearLayoutManager(this)
        movieList.adapter = MovieAdapter(processor)
    }

    override fun onResume() {
        super.onResume()

        processor.attachView(this)
        listEndDetector.setListEndListener(this)

        showProgress()
        observer.readOrFetchMovies()
    }

    override fun onPause() {
        super.onPause()

        processor.detachView()
        listEndDetector.removeListEndListener()
    }

    override fun moviesSet(numberOfFavourites: Int) {
        hideProgressAndError()
        movieList.adapter?.notifyDataSetChanged()
        setNumberOfFavouritesText(numberOfFavourites)
    }

    override fun moviesSetError(error: Throwable) {
        showError(buildErrorMessage(resources, R.string.init_movies_error, error))
    }

    override fun moviesAdded(numberAdded: Int) {
        hideProgressAndError()
        movieList.adapter?.let {
            it.notifyItemRangeInserted(it.itemCount - numberAdded, numberAdded)
        }
    }

    override fun moviesAddedError(error: Throwable) {
        showError(buildErrorMessage(resources, R.string.more_movies_error, error))
    }

    override fun onListEndReached() {
        showProgress()
        observer.fetchMoreMovies()
    }

    override fun toggleFavourite(movieRow: MovieRow) {
        observer.toggleFavourite(movieRow)
    }

    override fun onFavouriteChanged(position: Int, numberOfFavourites: Int) {
        movieList.adapter?.notifyItemChanged(position)
        setNumberOfFavouritesText(numberOfFavourites)
    }

    override fun onFavouriteChangedError(error: Throwable) {
        showError(buildErrorMessage(resources, R.string.favourite_movie_error, error))
    }

    private fun setNumberOfFavouritesText(numberOfFavourites: Int) {
        numberOfFavouritesText.text = if (numberOfFavourites > 0) numberOfFavourites.toString() else null
    }

    private fun showProgress() {
        errorMessage.text = null
        errorMessage.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    private fun showError(message: String) {
        errorMessage.text = message
        errorMessage.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
    }

    private fun hideProgressAndError() {
        errorMessage.text = null
        errorMessage.visibility = View.INVISIBLE
        progressBar.visibility = View.INVISIBLE
    }
}