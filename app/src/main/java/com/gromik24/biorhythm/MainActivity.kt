package com.gromik24.biorhythm

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

//константа для ключа сохранения состояния
private const val LAST_SELECTED_ITEM = "LAST_SELECTED_ITEM"
private val DAY_CHARS_FRAGMENT = DayCharsFragment().javaClass.name
private val INFO_FRAGMENT = InfoFragment().javaClass.name
private val CHECK_IDENTITY_FRAGMENT = CheckIdentityFragment().javaClass.name

class MainActivity : AppCompatActivity() {

    private var dayCharsFragment = DayCharsFragment()
    private var infoFragment = InfoFragment()
    private var checkIdentityFragment = CheckIdentityFragment()

    //ранняя инициализация нижней навигации
    private lateinit var bottomNavigationMenu: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        bottomNavigationMenu = findViewById(R.id.bottom_navigation_menu)

        //настроим клики по элементам нижней навигации
        bottomNavigationMenu.setOnItemSelectedListener { item ->
            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.day_chars -> {
                    fragment =
                        savedInstanceState?.let {
                            supportFragmentManager.getFragment(it, DAY_CHARS_FRAGMENT)
                        } ?: dayCharsFragment
                }
                R.id.info -> {
                    fragment =
                        savedInstanceState?.let {
                            supportFragmentManager.getFragment(it, INFO_FRAGMENT)
                        } ?: infoFragment
                }
                R.id.check_identity -> {
                    fragment =
                        savedInstanceState?.let {
                            supportFragmentManager.getFragment(it, CHECK_IDENTITY_FRAGMENT)
                        } ?: checkIdentityFragment
                }
            }
            replaceFragment(fragment!!)
            true
        }

        //восстановление состояния нижней навигации
        //если не сохранено то по дефолту выбрать R.id.dayChars
        bottomNavigationMenu.selectedItemId =
            savedInstanceState?.getInt(LAST_SELECTED_ITEM) ?: R.id.day_chars
    }
    //сохраним состояние последнего нажатого элемента нижней навигации
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(LAST_SELECTED_ITEM, bottomNavigationMenu.selectedItemId)

        //сохраняем инстанцию конкретного фрагмента
        val fragment = supportFragmentManager.fragments.last()
        supportFragmentManager.putFragment(outState, fragment.javaClass.name, fragment)
        super.onSaveInstanceState(outState)
    }

    //функция замены фрагментов с помощью supportFragmentManager
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}