package com.imhanjie.v2ex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.viewbinding.ViewBinding
import com.imhanjie.support.PreferencesManager
import com.imhanjie.v2ex.vm.BaseViewModel
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    protected lateinit var vb: VB

    protected val configSp: PreferencesManager = PreferencesManager.getInstance("app_config")

    abstract fun getViewModels(): List<BaseViewModel>

    abstract fun initViews()

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz: Class<VB> = type.actualTypeArguments[0] as Class<VB>
        val method = clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        vb = method.invoke(null, inflater, container, false) as VB

        for (vm in getViewModels()) {
            vm.error.observe(this) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }

        initViews()
        return vb.root
    }

}