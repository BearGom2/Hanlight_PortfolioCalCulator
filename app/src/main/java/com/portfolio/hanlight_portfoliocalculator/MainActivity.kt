package com.portfolio.hanlight_portfoliocalculator

import android.graphics.Color
import android.icu.util.IslamicCalendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.lang.NumberFormatException
import java.util.*
import javax.script.ScriptEngine
import javax.script.ScriptEngineFactory
import javax.script.ScriptEngineManager
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_0.setOnClickListener(listener)
        btn_1.setOnClickListener(listener)
        btn_2.setOnClickListener(listener)
        btn_3.setOnClickListener(listener)
        btn_4.setOnClickListener(listener)
        btn_5.setOnClickListener(listener)
        btn_6.setOnClickListener(listener)
        btn_7.setOnClickListener(listener)
        btn_8.setOnClickListener(listener)
        btn_9.setOnClickListener(listener)
        btn_backspace.setOnClickListener(listener)
        btn_C.setOnClickListener(listener)
        btn_Div.setOnClickListener(listener)
        btn_Mul.setOnClickListener(listener)
        btn_Ple.setOnClickListener(listener)
        btn_Min.setOnClickListener(listener)
        btn_Dot.setOnClickListener(listener)
        btn_Equal.setOnClickListener(listener)
        btn_openPar.setOnClickListener(listener)
        btn_clePar.setOnClickListener(listener)

    }

    var check: Int = 0

    val listener = object : View.OnClickListener {
        override fun onClick(v: View?) {

            var len: Int = edit_text.text.length

            if (v is Button) {
                if (v.id == btn_C.id) {
                    edit_text.setText("")
                    edit_text.setTextColor(Color.parseColor("#000000"))
                } else if (v.id == btn_openPar.id) {
                    edit_text.setText(edit_text.text.toString() + v.text.toString())
                    edit_text.setTextColor(Color.parseColor("#000000"))
                } else if (v.id == btn_clePar.id) {
                    edit_text.setText(edit_text.text.toString() + v.text.toString())
                    edit_text.setTextColor(Color.parseColor("#000000"))
                } else if (v.id == btn_Equal.id) {
                    calculation()
                } else if (v.id == btn_Ple.id) {
                    edit_text.setText(edit_text.text.toString() + v.text.toString())
                    edit_text.setTextColor(Color.parseColor("#000000"))
                    clicked_Sign(len)
                } else if (v.id == btn_Min.id) {
                    edit_text.setText(edit_text.text.toString() + v.text.toString())
                    clicked_Sign(len)
                    edit_text.setTextColor(Color.parseColor("#000000"))
                } else if (v.id == btn_Div.id) {
                    edit_text.setText(edit_text.text.toString() + v.text.toString())
                    clicked_Sign(len)
                    edit_text.setTextColor(Color.parseColor("#000000"))
                } else if (v.id == btn_Mul.id) {
                    edit_text.setText(edit_text.text.toString() + v.text.toString())
                    clicked_Sign(len)
                    edit_text.setTextColor(Color.parseColor("#000000"))
                } else if (v.id == btn_Dot.id) {
                    clicked_Dot(len)
                    edit_text.setText(edit_text.text.toString() + v.text.toString())
                    edit_text.setTextColor(Color.parseColor("#000000"))
                } else if (v.id == btn_0.id) {
                    edit_text.setText(edit_text.text.toString() + v.text.toString())
                    clicked_Num(len)
                    edit_text.setTextColor(Color.parseColor("#000000"))
                } else {
                    edit_text.setText(edit_text.text.toString() + v.text.toString())
                    clicked_Num(len)
                    edit_text.setTextColor(Color.parseColor("#000000"))
                }
            } else if (v is ImageButton) {
                if (v.id == btn_backspace.id) {
                    if (len > 0)
                        edit_text.setText(edit_text.text.toString().substring(0, len - 1))
                    edit_text.setTextColor(Color.parseColor("#000000"))
                }
            }
            //글자 수가 13글자 이상이 되면 가독성을 위해 폰트 크기 조절
            check()
        }
    }

    fun check() {
        if (edit_text.length() > 13 && check != 1) {
            edit_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32F)
            check = 1
        } else if (edit_text.length() <= 13 && check != 0) {
            edit_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40F)
            check = 0
        }
    }

    fun clicked_Num(len: Int) {
        var max: Int = get_Loc(edit_text.text.toString(), 1) + 1
        if (len > 0) {
            if (edit_text.text.substring(max, max + 1).toString().contains("0") && !(edit_text.text.substring(max, len).toString().contains("."))) {
                edit_text.text.replace(max, max + 1, "")
            }
        }
    }

    fun clicked_Dot(len: Int) { //소수점 관련 함수
        var max: Int = get_Loc(edit_text.text.toString(), 1) + 1
        if (len > 0) {
            var num: Int = 0
            for (a in 0..9) {
                if (!(edit_text.text.substring(len - 1, len).toString().contains(a.toString()))) {
                    num++
                    if (num > 9) {
                        edit_text.setText(edit_text.text.toString() + "0") //앞에 숫자가 아닐때
                    }
                }
            }
            if (edit_text.text.substring(max, len).toString().contains(".")) { //앞에 소수점이 또 나왔을때
                edit_text.text.replace(edit_text.text.lastIndexOf("."), len, "")
                if(edit_text.text.lastIndexOf("0") != -1){
                    edit_text.text.replace(edit_text.text.lastIndexOf("0"),edit_text.text.lastIndexOf("0")+1 , "")
                }

            }
        } else {
            edit_text.setText("0")
        }
    }

    fun get_Loc(str: String, type: Int): Int { // + - * / 의 기호 위치를 찾아주는 함수
        val list: ArrayList<Int> = arrayListOf()
        if (type == 1) {
            list.add(str.lastIndexOf(btn_Ple.text.toString()))
            list.add(str.lastIndexOf(btn_Min.text.toString()))
            list.add(str.lastIndexOf(btn_Div.text.toString()))
            list.add(str.lastIndexOf(btn_Mul.text.toString()))
            return Collections.max(list)
        } else {
            if (str.indexOf(btn_Ple.text.toString()) != -1) {
                list.add(str.indexOf(btn_Ple.text.toString()))
            }
            if (str.indexOf(btn_Min.text.toString()) != -1) {
                list.add(str.indexOf(btn_Min.text.toString()))
            }
            if (str.indexOf(btn_Div.text.toString()) != -1) {
                list.add(str.indexOf(btn_Div.text.toString()))
            }
            if (str.indexOf(btn_Mul.text.toString()) != -1) {
                list.add(str.indexOf(btn_Mul.text.toString()))
            }
            if (list.size != 0) {
                return Collections.min(list)
            } else {
                return 0;
            }
        }
    }

    fun clicked_Sign(len: Int) { //기호를 눌렀을 때 호출되는 함수
        if (len < 1) {
            edit_text.text.replace(0, len + 1, "")
        } else {
            if (edit_text.text.substring(len - 1, len).toString().contains(btn_Min.text.toString()) || edit_text.text.substring(len - 1, len).toString().contains(btn_Ple.text.toString())
                || edit_text.text.substring(len - 1, len).toString().contains(btn_Div.text.toString()) || edit_text.text.substring(len - 1, len).toString().contains(btn_Mul.text.toString())
                && !(edit_text.text.substring(len - 1, len + 1).toString().contains("."))) { edit_text.text.replace(len - 1, len, "")
            }
        }
    }

    fun calculation() {
        val numlist: ArrayList<Double> = arrayListOf()
        val signlist: ArrayList<String> = arrayListOf()
        val list: ArrayList<Any> = arrayListOf()
        var str: String = edit_text.text.toString()
        var save_str: String = ""
        var a: Int = 0
        var num: Int = 0

        list.clear()

        while (str.indexOf(")", 0) != -1) { // 괄호 우선 계산을 위한 while, 괄호가 있는지 체크
            save_str = str.substring((str.indexOf("(")) + 1, str.indexOf(")"))
            val parlist: List<String> = (save_str.split((btn_Ple.text.toString()), btn_Min.text.toString(), btn_Div.text.toString(), btn_Mul.text.toString()))

            for (a in 0..parlist.size - 1) { //parlist에 있던 숫자와 기호를 list에 전체 대입
                var min: Int = get_Loc(save_str, 0)

                list.add(parlist[a])
                if (a != parlist.size - 1) {
                    if (min != 0) {
                        list.add(save_str.substring(min, min + 1))
                        save_str = save_str.replaceFirst(save_str.substring(min, min + 1), "")
                    }

                }
            }
            cal(list)
            str = str.replaceFirst(str.substring((str.indexOf("(")), str.indexOf(")") + 1), list[num].toString())
            num++
        }
        list.clear()

        val parlist: List<String> = (str.split((btn_Ple.text.toString()), btn_Min.text.toString(), btn_Div.text.toString(), btn_Mul.text.toString()))

        for (a in 0..parlist.size - 1) { //parlist에 있던 숫자와 기호를 list에 전체 대입
            var min: Int = get_Loc(str, 0)

            list.add(parlist[a])
            if (a != parlist.size - 1) {
                if (min != 0) {
                    list.add(str.substring(min, min + 1))
                    str = str.replaceFirst(str.substring(min, min + 1), "")
                }

            }
        }
        cal(list)
        edit_text.setText(list[0].toString())
        edit_text.setTextColor(Color.parseColor("#039BE5"))
    }


    fun cal(list: ArrayList<Any>) {
        try {
            for (a in 0..list.size - 1) {
                var Mul: Int = list.indexOf(btn_Mul.text.toString())
                var Div: Int = list.indexOf(btn_Div.text.toString())

                if (Div == -1 && Mul > -1 || Div < Mul && Div > -1) { //곱하기 상황
                    list[Mul] = java.lang.Double.parseDouble(list[Mul - 1].toString()) * java.lang.Double.parseDouble(list[Mul + 1].toString())
                    list.removeAt(Mul + 1)
                    list.removeAt(Mul - 1)
                    for (a in 0..list.size - 1) {
                        var Ple: Int = list.indexOf(btn_Ple.text.toString())
                        var Min: Int = list.indexOf(btn_Min.text.toString())

                        if (Min == -1 && Ple > -1 || Min < Ple && Min > -1) { //더하기 상황
                            list[Ple] = java.lang.Double.parseDouble(list[Ple - 1].toString()) + java.lang.Double.parseDouble(list[Ple + 1].toString())
                            list.removeAt(Ple + 1)
                            list.removeAt(Ple - 1)
                        } else if (Ple == -1 && Min > -1 || Ple < Min && Ple > -1) { //빼기 상황
                            list[Min] = java.lang.Double.parseDouble(list[Min - 1].toString()) - java.lang.Double.parseDouble(list[Min + 1].toString())
                            list.removeAt(Min + 1)
                            list.removeAt(Min - 1)
                        }
                    }
                } else if (Mul == -1 && Div > -1 || Mul < Div && Mul > -1) { //나누기
                    list[Div] = java.lang.Double.parseDouble(list[Div - 1].toString()) / java.lang.Double.parseDouble(list[Div + 1].toString())
                    list.removeAt(Div + 1)
                    list.removeAt(Div - 1)
                    for (a in 0..list.size - 1) {
                        var Ple: Int = list.indexOf(btn_Ple.text.toString())
                        var Min: Int = list.indexOf(btn_Min.text.toString())

                        if (Min == -1 && Ple > -1 || Min < Ple && Min > -1) { //더하기 상황
                            list[Ple] = java.lang.Double.parseDouble(list[Ple - 1].toString()) + java.lang.Double.parseDouble(list[Ple + 1].toString())
                            list.removeAt(Ple + 1)
                            list.removeAt(Ple - 1)
                        } else if (Ple == -1 && Min > -1 || Ple < Min && Ple > -1) { //빼기 상황
                            list[Min] = java.lang.Double.parseDouble(list[Min - 1].toString()) - java.lang.Double.parseDouble(list[Min + 1].toString())
                            list.removeAt(Min + 1)
                            list.removeAt(Min - 1)
                        }
                    }
                } else { //곱하거나 나누는 기호가 없을 때
                    for (a in 0..list.size - 1) {
                        var Ple: Int = list.indexOf(btn_Ple.text.toString())
                        var Min: Int = list.indexOf(btn_Min.text.toString())

                        if (Min == -1 && Ple > -1 || Min < Ple && Min > -1) {//더하기
                            list[Ple] = java.lang.Double.parseDouble(list[Ple - 1].toString()) + java.lang.Double.parseDouble(list[Ple + 1].toString())
                            list.removeAt(Ple + 1)
                            list.removeAt(Ple - 1)
                        } else if (Ple == -1 && Min > -1 || Ple < Min && Ple > -1) { //빼기
                            list[Min] = java.lang.Double.parseDouble(list[Min - 1].toString()) - java.lang.Double.parseDouble(list[Min + 1].toString())
                            list.removeAt(Min + 1)
                            list.removeAt(Min - 1)
                        }
                    }
                }
            }
        } catch (e: NumberFormatException) { //숫자로 변환하지 못 한 다면 catch 발동
            Toast.makeText(this@MainActivity, "계산식이 잘 못 되었습니다.", Toast.LENGTH_SHORT).show()
            list[0] = (edit_text.text)
        }
    }
}


