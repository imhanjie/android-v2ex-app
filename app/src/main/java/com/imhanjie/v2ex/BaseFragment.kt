package com.imhanjie.v2ex

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.viewbinding.ViewBinding
import com.imhanjie.support.PreferencesManager
import com.imhanjie.support.e
import com.imhanjie.support.ext.toast
import com.imhanjie.v2ex.common.GlobalViewModel
import com.imhanjie.v2ex.common.SpConstants
import com.imhanjie.v2ex.common.getVBClass
import com.imhanjie.v2ex.model.VMEvent
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.widget.dialog.PureLoadingDialog

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    protected lateinit var vb: VB

    protected val configSp: PreferencesManager = PreferencesManager.getInstance(SpConstants.FILE_APP_CONFIG)
    protected lateinit var loadingDialog: PureLoadingDialog
    protected lateinit var globalViewModel: GlobalViewModel

    abstract fun initViewModels(): List<BaseViewModel>

    abstract fun initViews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        globalViewModel = ViewModelProvider(requireActivity().applicationContext as App).get(GlobalViewModel::class.java)
        e("${javaClass.simpleName} : onCreate()")
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        e("${javaClass.simpleName} : onCreateView()")
        val vbClass = getVBClass<VB>(javaClass)
        val method = vbClass.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        vb = method.invoke(null, inflater, container, false) as VB
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        e("${javaClass.simpleName} : onViewCreated()")
        loadingDialog = PureLoadingDialog(requireContext()).apply {
            setCancelable(false)
        }
        for (vm in initViewModels()) {
            vm.event.observe(viewLifecycleOwner) {
                when (it.event) {
                    VMEvent.Event.SHOW_LOADING -> loadingDialog.update(false)
                    VMEvent.Event.HIDE_LOADING -> loadingDialog.update(true)
                    VMEvent.Event.TOAST, VMEvent.Event.ERROR -> toast(it.text)
                }
            }
        }
        initViews()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        e("${javaClass.simpleName} : onAttach()")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        e("${javaClass.simpleName} : onActivityCreated()")
    }

    override fun onStart() {
        super.onStart()
        e("${javaClass.simpleName} : onStart()")
    }

    override fun onResume() {
        super.onResume()
        e("${javaClass.simpleName} : onResume()")
    }

    override fun onPause() {
        super.onPause()
        e("${javaClass.simpleName} : onPause()")
    }

    override fun onStop() {
        super.onStop()
        e("${javaClass.simpleName} : onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        e("${javaClass.simpleName} : onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        e("${javaClass.simpleName} : onDestroy()")
    }

    override fun onDetach() {
        super.onDetach()
        e("${javaClass.simpleName} : onDetach()")
    }

    // 递归向上寻找 ViewBinding 泛型
//    @Suppress("UNCHECKED_CAST")
//    private fun getVBClass(enterClazz: Class<*>): Class<*> {
//        val type = enterClazz.genericSuperclass as ParameterizedType
//        val clazz: Class<VB> = type.actualTypeArguments[0] as Class<VB>
//        return if (ViewBinding::class.java.isAssignableFrom(clazz)) {
//            clazz
//        } else {
//            getVBClass(enterClazz.superclass as Class<*>)
//        }
//    }

}