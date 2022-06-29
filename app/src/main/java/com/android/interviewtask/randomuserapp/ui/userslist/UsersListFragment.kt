package com.android.interviewtask.randomuserapp.ui.userslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.android.interviewtask.randomuserapp.R
import com.android.interviewtask.randomuserapp.adapter.UserListAdapter
import com.android.interviewtask.randomuserapp.databinding.FragmentUsersListBinding
import com.android.interviewtask.randomuserapp.model.UsersList
import com.android.interviewtask.randomuserapp.ui.userdetails.ARG_USERITEM
import com.android.interviewtask.randomuserapp.utils.UIState
import com.android.interviewtask.randomuserapp.utils.showErrorMsg
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersListFragment : Fragment() {

    private val binding by lazy{
        FragmentUsersListBinding.inflate(layoutInflater)
    }
    private val userViewModel: UserViewModel by viewModel()
    private lateinit var usersAdapter: UserListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        usersAdapter=UserListAdapter()
        binding.usersList.adapter=usersAdapter
        userViewModel.usersLiveData.observe(viewLifecycleOwner,{uistate->
            when(uistate){
                is UIState.LOADING -> {}
                is UIState.SUCCESS -> {
                    val usersList=uistate.success as UsersList
                    usersAdapter.loadData(usersList.userItems)
                }
                is UIState.ERROR -> {
                    showErrorMsg(binding.root,uistate.error.localizedMessage.toString())
                }
            }

        })
        usersAdapter.onItemClick={useritem ->
            val bundle=Bundle().apply{
                putSerializable(ARG_USERITEM,useritem)
            }
            findNavController().navigate(R.id.action_interviewtaskListFragment_to_interviewtaskDetailFragment,bundle)
        }
        userViewModel.subscribeToUsersList()
        return binding.root
    }
}