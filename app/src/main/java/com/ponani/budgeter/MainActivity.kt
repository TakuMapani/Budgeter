package com.ponani.budgeter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.ui.AppBarConfiguration
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ponani.budgeter.Utilities.Constants
import com.ponani.budgeter.database.SpendingItem
import com.ponani.budgeter.ui.SpendingListAdapter
import com.ponani.budgeter.viewModels.MainViewModel

class MainActivity : AppCompatActivity() {

    val SPENDING_DIALOG = "SPENDING_FRAG"

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var spendingViewModel: MainViewModel

    /**
     * Local variable that receives broadcast for undoing delete
     */
    val reAddDeletedBrodCastReceiver = object : BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            val task : Int  = intent.getIntExtra(Constants.RE_ADD_SPENDING_STR,Constants.RE_ADD_SPENDING)
            val item : SpendingItem = Constants.TEMP_ITEM
            spendingViewModel.insertSpendingItem(item)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            showDialog()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        //val navView: NavigationView = findViewById(R.id.nav_view)
        //val navController = findNavController(R.id.recyclerViewSpending)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send
            ), drawerLayout
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        //navView.setupWithNavController(navController)
        //todo: remove useless junk
        spendingViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewSpending)
        val adapter = SpendingListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?

        spendingViewModel.spendingList.observe(this, Observer { spendingList ->
            spendingList?.let { adapter.setSpending(it as MutableList<SpendingItem>) }
        })
        spendingViewModel.total.observe(this, Observer { total -> adapter.setSpendingTotal(total)})

        /**
         * function to handle swipping recyclerview item
         */
        val touchHelperCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var position: Int = viewHolder.adapterPosition
                var item: SpendingItem = adapter.deleteItem(position, recyclerView)
                spendingViewModel.deleteItem(item)
            }

        }

        //create ItemTouchHelper and attach it to the recycler view
        val itemTouchHelper = ItemTouchHelper(touchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        //Register the broadcast receive to allow it to receive broadcasts and act on them.
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(reAddDeletedBrodCastReceiver, IntentFilter(Constants.UNDODELETE))
    }

    //Opens Spending Dialog box from floating action button
    //No items are passed to the dialog box
    //Dialog box set nonCancellable
    private fun showDialog() {
        var spendingDialog : SpendingDialog = SpendingDialog().newInstance()
        spendingDialog.show(supportFragmentManager,SPENDING_DIALOG)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.recyclerViewSpending)
//        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id: Int = item.itemId
        if (id == R.id.action_settings) {
            spendingViewModel.insertSampleData()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        //unregister receiver to prevent memory leaks.
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(reAddDeletedBrodCastReceiver)
        super.onDestroy()
    }


}
