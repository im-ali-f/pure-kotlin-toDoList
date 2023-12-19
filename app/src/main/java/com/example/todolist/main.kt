package com.example.todolist
import java.io.File



//var accessList= mutableListOf("loginSignup","taskMenu")
//var accessList= mutableListOf("loginSignup")
var accessList= mutableListOf("taskMenu")

class ConsoleView(var accessList:List<String>,var username :String?=null){
    var accessMap= HashMap<Int,String>()
    var listCounter=0
    fun menu(){
        var chosenMenu=0
        var menuRange=false
        println("choose section")
        //now check our access + put access to access map
        for(access in accessList){
            listCounter+=1
            accessMap.put(listCounter,access)
            println("$listCounter - $access")

        }
        while(! menuRange){
        //now waits for user choose
        print("choosen Menu =")
        chosenMenu= readln().toInt()
        var menuRangeRange=1..listCounter
        if(chosenMenu in menuRangeRange)break
        }
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
        if (chosenMenuStr == "taskMenu"){
            var taskManagerObj=TaskManagerClass(username)
            taskManagerObj.show()
        }
    }
}

class TaskManagerClass(var username: String?){
    var taskList= HashMap<String,List<String>>()
    fun findTaskList(){
        username= "ali" // in pak she
        val filePath = "app/src/main/java/com/example/todolist/$username.txt"
        File(filePath).forEachLine{line->
            var TTDTArray=line.split("=")
            var TDTList= listOf<String>(TTDTArray[1],TTDTArray[2],TTDTArray[3],)
            taskList.put(TTDTArray[0],TDTList)
            println(taskList)
        }
    }
    fun show(){
        print("\n\n\n\n\n\n\n\n\n")//insted of clear :)
        findTaskList()
        println("$username you are at task manager(crud) choose a menu !")
        println("1-task list")
        println("2-create task")
        println("3-update task")
        println("4-remove task")
        println("5-back to Menu")
        var rangeMenu=1..5
        var chosenMenuInt=0
        while(true){
            print("choose menu=")
            chosenMenuInt = readln().toInt()
            if(chosenMenuInt in rangeMenu)break
        }
        if(chosenMenuInt == 5){
            print("\n\n\n\n\n\n\n\n\n")//insted of clear :)
            var backToMenu= ConsoleView(accessList,username)
            backToMenu.menu()
        }
        choseCRUD(chosenMenuInt)
    }
    fun choseCRUD(chosenMenuInt: Int){
        if(chosenMenuInt == 1){

        }
    }
}







class LoginSignupClass{
    var loggedInUser :String? = null
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
                        loggedInUser=username
                        accessList.clear()
                        accessList.add("taskMenu")
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

        //hala bargardim menu
        print("\n\n\n\n\n\n\n\n\n")//insted of clear :)
        val backToMenu=ConsoleView(accessList, loggedInUser)
        backToMenu.menu()
    }

}
fun main(){
    val starterObj=ConsoleView(accessList,null)
    starterObj.menu()
}