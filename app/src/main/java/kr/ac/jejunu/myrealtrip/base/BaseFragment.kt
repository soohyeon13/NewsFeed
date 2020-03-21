package kr.ac.jejunu.myrealtrip.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment<VDB : ViewDataBinding, VM : BaseViewModel>(
    private val layoutId: Int
) : Fragment() {
    protected lateinit var binding:VDB
    abstract val viewModel : VM
//    protected lateinit var viewModel : VM

//    protected abstract fun getViewModel() :Class<VM>
//    protected abstract fun getBindingVariable() : Int
    protected abstract fun initView()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,layoutId,container,false)
        binding.lifecycleOwner = this
//        this.viewModel = if (::viewModel.isInitialized) viewModel else ViewModelProvider(this).get(getViewModel())
//        binding.setVariable(getBindingVariable(),viewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    companion object {
        fun <T: Fragment> newInstance(fragment:T) : T {
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}