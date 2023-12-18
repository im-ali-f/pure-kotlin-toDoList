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
        var chosenMenu= readln().toInt()
        chosenMenuNavigator(chosenMenu,accessMap)
    }
    fun chosenMenuNavigator(chosenMenuInt: Int,accessMap:HashMap<Int,String>){
        var chosenMenuStr:String?=null
        for(access in accessMap){
            if(chosenMenuInt == access.key) chosenMenuStr=access.value
        }
        if (chosenMenuStr == "loginSignup"){
            var loginSignupObj=LoginSignupClass()
            loginSignupObj.show()
        }
    }
}

class LoginSignupClass{
    fun show(){

    }
}
fun main(){

    val starterObj=ConsoleView(accessList)
    starterObj.menu()
}