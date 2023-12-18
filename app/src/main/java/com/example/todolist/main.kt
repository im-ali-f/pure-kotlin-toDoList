package com.example.todolist

var accessList=arrayOf("loginSignup","taskMenu")
class ConsoleView(var accessList:Array<String>){
    var accessMap= HashMap<Int,String>()
    var listCounter=0
    fun menu(){
        println("choose section")
        //now check our access + put access to access map
        for(access in accessList){
            listCounter+=1
            accessMap.put(listCounter,access)
            println("$listCounter - $access")

        }
        //now waits for user choose
        print("choosen Menu =")
        var choosenMenu= readln().toInt()
        println(choosenMenu)
    }
}

fun main(){

    val starterObj=ConsoleView(accessList)
    starterObj.menu()
}