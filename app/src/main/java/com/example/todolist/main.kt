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
    var loggedInUser :Int? = null
    fun show(){
        print("\n\n\n\n\n\n\n\n\n")//insted of clear :)
        println("login/signup Section !")
        var menuRange=false
        var chosenMenuIntLS=0
        while(! menuRange){
            println("1-login \n2-signup")
            print("your choice =")
            chosenMenuIntLS= readln().toInt()
            if(chosenMenuIntLS == 1 || chosenMenuIntLS ==2)menuRange=true
        }
        chosenLS(chosenMenuIntLS)

    }
    fun chosenLS(chosen:Int){
        var userList= mapOf<String,String>(
            "ali" to "333",
            "mmd" to "4444",
            "reza" to "55555",

        )
        print("\n\n\n\n\n\n\n\n\n")//insted of clear :)
        if(chosen == 1){
            println("logging in ...")
            while(true) {
                print("username =")
                var username = readln()
                print("\npassword =")
                var password = readln()
                if (userList.containsKey(username)) {
                    if (userList[username] == password) {
                        print("logged in !")
                        //inja access jadid bedam
                        break
                    } else {
                        println("wrong password")
                    }
                } else {
                    println("wrong username")
                }
            }
        }
        if(chosen == 2){
            println("siggnin up ...")
            print("username =")
            var username=readln()
            print("password =")
            var password=readln()
        }

        //inja bargarim menu
    }

}
fun main(){

    val starterObj=ConsoleView(accessList)
    starterObj.menu()
}