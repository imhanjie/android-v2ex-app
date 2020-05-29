package com.imhanjie.v2ex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.viewbinding.ViewBinding
import com.imhanjie.support.PreferencesManager
import com.imhanjie.support.ext.toast
import com.imhanjie.v2ex.common.GlobalViewModel
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.widget.dialog.PureLoadingDialog
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    protected lateinit var vb: VB

    protected val configSp: PreferencesManager = PreferencesManager.getInstance("app_config")
    protected lateinit var loadingDialog: PureLoadingDialog
    protected lateinit var globalViewModel: GlobalViewModel

    abstract fun getViewModels(): List<BaseViewModel>

    abstract fun initViews()

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val method = getVBClass(javaClass).getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        vb = method.invoke(null, inflater, container, false) as VB

        loadingDialog = PureLoadingDialog(requireContext()).apply {
            setCancelable(false)
        }
        for (vm in getViewModels()) {
            vm.error.observe(viewLifecycleOwner) { toast(it) }
            vm.toast.observe(viewLifecycleOwner) { toast(it) }
            vm.loadingDialogState.observe(viewLifecycleOwner) { loadingDialog.update(!it) }
        }

        globalViewModel = ViewModelProvider(requireActivity().applicationContext as App).get(GlobalViewModel::class.java)

        initViews()
        return vb.root
    }

    // TODO 重构，以及 Activity 内的
    private fun getVBClass(enterClazz: Class<*>): Class<*> {
        val type = enterClazz.genericSuperclass as ParameterizedType
        val clazz: Class<VB> = type.actualTypeArguments[0] as Class<VB>
        if (ViewBinding::class.java.isAssignableFrom(clazz)) {
            return clazz
        } else {
            return getVBClass(enterClazz.superclass as Class<*>)
        }
    }

}