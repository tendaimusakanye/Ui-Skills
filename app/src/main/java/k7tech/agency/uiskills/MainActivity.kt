package k7tech.agency.uiskills

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import k7tech.agency.uiskills.databinding.ActivityMainBinding
import k7tech.agency.uiskills.widget.MyBottomSheet.BottomSheetState

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment

        navController = navHost.navController
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.feedFragment, R.id.searchFragment, R.id.settingsFragment)
        )
        with(binding) {
            bottomNav.setupWithNavController(navController)

            myBottomSheet.setOnDimClickListener {
                myBottomSheet.setSheetState(BottomSheetState.HIDDEN)
            }

            viewModel.title.observe(this@MainActivity, {
                sheetItemTitle.text = it
                myBottomSheet.setSheetState(BottomSheetState.EXPANDED)
            })

            buttonCheckItem.setOnClickListener {
                val itemTitle = sheetItemTitle.text.toString()
                viewModel.checkItemWithTitle(Pair(true, itemTitle))
                myBottomSheet.setSheetState(BottomSheetState.HIDDEN)
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    override fun onBackPressed() {
        with(binding) {
            if (myBottomSheet.getSheetState() == BottomSheetState.EXPANDED) {
                myBottomSheet.setSheetState(BottomSheetState.HIDDEN)
            } else {
                super.onBackPressed()
            }
        }
    }
}