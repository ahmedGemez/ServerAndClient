package com.example.clientapp.presentation.ui.homefragment

import android.Manifest
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.clientapp.R
import com.example.clientapp.databinding.FragmentHomeBinding
import com.example.clientapp.presentation.ClientApp
import com.example.clientapp.presentation.di.AppComponent
import com.example.clientapp.presentation.di.ViewModelFactory
import com.example.serverapp.OperationAidl
import com.example.serverapp.OperationDto
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class HomeFragment : Fragment() {

    private var addService: OperationAidl? = null
    private val serverAppPackage: String = "com.example.serverapp"
    private val action: String = "service.calc"
    private lateinit var operationDto: OperationDto
    private lateinit var listAdapter: OperationAdapter

    var NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()
    var executor = ThreadPoolExecutor(
        NUMBER_OF_CORES * 2,
        NUMBER_OF_CORES * 2,
        60L,
        TimeUnit.SECONDS,
        LinkedBlockingQueue()
    )

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: HomeViewModel
    private lateinit var fragmentHomeBinding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var appComponent: AppComponent =
            (requireActivity().application as ClientApp).getAppComponent()
        appComponent.inject(this)

        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get<HomeViewModel>(
            HomeViewModel::class.java)

        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }


        fragmentHomeBinding.lifecycleOwner = viewLifecycleOwner

        return fragmentHomeBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllOperations()

        runObserves()
        initConnection()
        setupListAdapter()

    }

    private fun initConnection() {
        if (addService == null) {
            val intent = Intent(OperationAidl::class.java.getName())
            /*this is service name which has been declared in the server's manifest file in service's intent-filter*/
            intent.action = action
            /*From 5.0 annonymous intent calls are suspended so replacing with server app's package name*/
            intent.setPackage(serverAppPackage)
            // binding to remote service
            requireActivity().bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE)
        }
    }

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            Log.d("Tag", "Service Connected")
            addService = OperationAidl.Stub.asInterface(iBinder as IBinder)
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            Log.d("Tag", "Service Disconnected")
            addService = null
        }
    }


    fun executeWorkerThread(operationDto: OperationDto) {
        executor.execute(Runnable {
            try {
                val operationDto = addService?.calculateNumbers(
                    operationDto,
                    (viewModel.time.value)?.toLong() ?: 0
                )
                requireActivity().runOnUiThread(Runnable() {
                    Toast.makeText(requireContext(), "toast", Toast.LENGTH_SHORT).show()
                    viewModel.updateOperation(operationDto!!)
                })
            } catch (exception: RemoteException) {
                Log.i("TAG", "Thread for interrupted")
            }
        })
    }


    private fun runObserves() {
        observeSuccessAdd()
        observeLastItem()
        observeitems()
        navigateToLocation()
    }

    private fun observeSuccessAdd() {
        viewModel.sucessAdd.observe(viewLifecycleOwner, Observer {
            viewModel.getLastOperation()
            fragmentHomeBinding.operationRec.smoothScrollToPosition(0);

        })
    }

    private fun observeLastItem() {
        viewModel.lastItem.observe(viewLifecycleOwner, Observer {

            if (appInstalledOrNot(serverAppPackage))
                executeWorkerThread(it)
            else
                Toast.makeText(requireContext(),"Server App Not Installed",Toast.LENGTH_LONG).show()

        })
    }

    private fun observeitems() {
        viewModel.items.observe(viewLifecycleOwner, Observer {
            listAdapter.submitList(it)
            listAdapter.notifyDataSetChanged()
            fragmentHomeBinding.operationRec.smoothScrollToPosition(0);

        })
    }
    private fun navigateToLocation() {
        viewModel.locationEvent.observe(viewLifecycleOwner, Observer {
            val currentFragment = NavHostFragment.findNavController(this).currentDestination?.id
            if(currentFragment == R.id.homeFragment){
                val action   =  HomeFragmentDirections.actionHomeFragmentToLocationFragment();
                findNavController().navigate(action)
            }

        })
    }


    private fun setupListAdapter() {
        if (viewModel != null) {
            listAdapter =
                OperationAdapter(
                    viewModel
                )
            fragmentHomeBinding.operationRec.adapter = listAdapter
        } else {
            Log.d(
                "FragmentBudgetsBinding",
                "ViewModel not initialized when attempting to set up adapter."
            )
        }
    }



    private fun appInstalledOrNot(uri: String): Boolean {
        val pm: PackageManager = requireActivity().getPackageManager()
        val app_installed: Boolean
        app_installed = try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
        return app_installed
    }


    override fun onDestroy() {
        super.onDestroy()
        requireActivity().unbindService(serviceConnection)
        executor.shutdownNow()

    }
}