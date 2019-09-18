package com.kiran.evoketest.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kiran.evoketest.android.model.User
import android.R.attr.mode
import android.content.Context
import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.net.ConnectivityManager
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ItemSizeCallBack {
    private var userAdapter: UserAdapter? = null
    private var userList: ArrayList<User> = ArrayList()
    private var itemListViewModel: ItemListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        itemListViewModel = ViewModelProviders.of(this)
            .get(ItemListViewModel::class.java).apply {
                (application as App).getAppComponent()?.inject(this)
            }
        (application as App).getAppComponent()?.inject(this)
    }

    fun getUserData(view: View) {
        if (isNetworkConnected()) {
            get_users_btn.visibility = View.GONE
            progress.visibility = View.VISIBLE
            no_data_tv.visibility = View.GONE
            itemListViewModel?.let {
                it.getUsers().observe(
                    this,
                    Observer<ArrayList<User>> { result ->
                        result?.let {
                            userList = it
                            setupRecyclerView(item_list, userList)
                        }
                    })
            }
        } else showNetworkError()
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        userList: ArrayList<User>
    ) {
        progress.visibility = View.GONE
        if (userList.size > 0) {
            no_data_tv.visibility = View.GONE
        } else {
            no_data_tv.text = resources.getString(R.string.no_data)
            no_data_tv.visibility = View.VISIBLE
        }
        userAdapter = UserAdapter(this, userList)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = userAdapter
        val itemDecor = DividerItemDecoration(this, HORIZONTAL)
        recyclerView.addItemDecoration(itemDecor)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            menuInflater.inflate(R.menu.menu, menu)
            val searchView = menu.findItem(R.id.action_search).actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    if (userAdapter != null)
                        userAdapter!!.filter.filter(query)
                    return false
                }

                override fun onQueryTextChange(query: String): Boolean {
                    if (userAdapter != null)
                        userAdapter!!.filter.filter(query)
                    return false
                }
            })
            return true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onItemSizeChanged(size: Int) {
        if (size > 0)
            no_data_tv.visibility = View.GONE
        else {
            no_data_tv.text = resources.getString(R.string.no_data)
            no_data_tv.visibility = View.VISIBLE
        }
    }

    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
    }

    private fun showNetworkError() {
        Snackbar.make(
            main_container,
            resources.getString(R.string.internet_error),
            Snackbar.LENGTH_LONG
        ).show()
    }
}
