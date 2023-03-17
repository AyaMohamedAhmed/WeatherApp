package com.example.weatherapp.view.favourite.view

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentAddFavouriteLocationBinding
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.model.Repository
import com.example.weatherapp.utils.FavouriteApiState
import com.example.weatherapp.utils.Utils
import com.example.weatherapp.view.favourite.viewmodel.FavouriteViewModel
import com.example.weatherapp.view.favourite.viewmodel.FavouriteViewModelFactory

private lateinit var binding: FragmentAddFavouriteLocationBinding
private lateinit var favouriteViewModel: FavouriteViewModel
private lateinit var favouriteViewModelFactory: FavouriteViewModelFactory
private lateinit var repository: Repository


class AddFavouriteLocationFragment : Fragment(), FavouriteOnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddFavouriteLocationBinding.inflate(inflater, container, false)
        /*val localSource = LocalSourceImpl(requireContext())
        val apiClient = APIClient.getInstane()
        repository = Repository(localSource, apiClient)

*/      repository = Repository.getInstance(requireActivity().application)
        favouriteViewModelFactory = FavouriteViewModelFactory(repository)
        favouriteViewModel =
            ViewModelProvider(this, favouriteViewModelFactory).get(FavouriteViewModel::class.java)
        favouriteViewModel.getFavourite()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(Utils.isOnline(requireContext())) {
            binding.addFavourite.setOnClickListener {
                findNavController().navigate(R.id.action_addFavouriteLocationFragment_to_favourite)
            }
            lifecycleScope.launchWhenCreated {
                favouriteViewModel.data.collect {
                    when (it) {
                        is FavouriteApiState.FavSuccess -> {
                            binding.favouriteRecycleView.adapter =
                                FavouriteAdapter(it.data, this@AddFavouriteLocationFragment)
                            Log.i("TAG", "onViewCreated: " + it.data)
                            binding.favouriteRecycleView.layoutManager =
                                LinearLayoutManager(requireContext())
                            binding.favouriteRecycleView.apply {
                                layoutManager = LinearLayoutManager(requireContext()).apply {
                                    orientation = RecyclerView.VERTICAL
                                }
                            }
                        }

                        is FavouriteApiState.FavFailure -> {
                            Toast.makeText(requireContext(), "${it.msg}", Toast.LENGTH_LONG).show()
                        }
                        else -> {
                        }
                    }
                }

            }
        }
        else{
            binding.imageView11.visibility=View.VISIBLE
            binding.addFavourite.visibility=View.GONE
            binding.favouriteRecycleView.visibility=View.GONE
        }
    }


    override fun deleteFavouriteLocation(favorite: Favorite) {
        val alertBuild: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alertBuild.setTitle("Delete Location")
        alertBuild.setMessage("DO YOU WANT TO DELETE THIS FAVOURITE PLACE?")
        alertBuild.setPositiveButton("Delete") { _, _ ->
            favouriteViewModel.deleteFavourite(favorite)
        }

        alertBuild.setNegativeButton("NOPE!") { _, _ ->

        }

        val alert = alertBuild.create()
        alert.show()
    }

    override fun onClick(favorite: Favorite) {
        val favBundleHome:Bundle= Bundle()

        favBundleHome.putSerializable("fav",favorite)
        findNavController().navigate(R.id.home,favBundleHome)

    }

}



