package com.example.todolist
import android.annotation.TargetApi
import android.os.Build
import java.io.File
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.io.BufferedWriter
import java.io.FileWriter



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
            //waits for user choose
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
        taskList.clear()
        username= "ali" // in pak she
        val filePath = "app/src/main/java/com/example/todolist/$username.txt"
        File(filePath).forEachLine{line->
            var TTDTArray=line.split("=")
            var TDTList= listOf<String>(TTDTArray[1],TTDTArray[2],TTDTArray[3],)
            taskList.put(TTDTArray[0],TDTList)
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
        chosenCRUD(chosenMenuInt)
    }
    @TargetApi(Build.VERSION_CODES.O)
    fun chosenCRUD(chosenMenuInt: Int){
        print("\n\n\n\n\n\n\n\n\n")//insted of clear :)
        //READ
        if(chosenMenuInt == 1){
            for (task in taskList){
                println("--------------${task.key}--------------")
                println("text = ${task.value[0]}")
                println("date = ${task.value[1]} \ntime = ${task.value[2]}")
            }
            print("press any key to back to TaskManager")
            readlnOrNull()
            print("\n\n\n\n\n\n\n\n\n")//insted of clear :)
            show()
        }
        //CREATE
        if(chosenMenuInt ==2){
            var title=""
            var text=""
            var date=""
            var time=""
            var parsedTime:LocalTime
            var parsedDate:LocalDate
            while(true){
                print("enter Title=")
                title= readln()
                if(!title.isEmpty()) break
            }
            while(true){
                print("enter Text=")
                text=readln()
                if(!text.isEmpty()) break
            }
            while(true){
                print("enter Date(format -> dd-mm-yyyy) =")
                date=readln()
                if(!date.isEmpty()){
                    //thanks chatgpt for this part
                    try {
                        val pattern = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                        parsedDate = LocalDate.parse(date, pattern)
                        break
                    }
                    catch (e: DateTimeParseException) {
                        println("format ya date dorost vared nashode ast !")
                    }
                }
            }
            while(true){
                print("enter Time(format -> hh:mm:ss)=")
                time=readln()
                if(!time.isEmpty()) {
                    //thanks chatgpt for this part too
                    try{
                        val pattern = DateTimeFormatter.ofPattern("HH:mm:ss")
                        parsedTime = LocalTime.parse(time,pattern)
                        break
                    }
                    catch (e:DateTimeParseException){
                        println("format ya time dorost vared nashode ast !")
                    }
                }
            }
            var TDTList= listOf<String>(text,date,time)
            taskList.put(title,TDTList)
            var content:String=""
            var firstTime=true
            println(taskList)
            for(task in taskList){
                if(firstTime){
                    content="${task.key}=${task.value[0]}=${task.value[1]}=${task.value[2]}"
                    firstTime=false}
                else
                    content=content+"\n"+"${task.key}=${task.value[0]}=${task.value[1]}=${task.value[2]}"
            }
            var filePath="app/src/main/java/com/example/todolist/$username.txt"
            File(filePath).bufferedWriter().use { writer ->
                writer.write(content)
                println("Content has been written to the file.")
            }
            show()
        }

        if(chosenMenuInt == 3){
            var counter=1
            println("choose a task by its number")
            for (task in taskList){
                println("$counter => ${task.key}")
                counter+=1
            }
            var chooseRange=1..counter
            var chosen=0
            while(true){
                print("which one ? =")
                chosen=readln().toInt()
                if(chosen in chooseRange)break
            }
            var findTaskCounter=0

            var newKey=""
            var oldKey=""
            var newText=""
            var newDate=""
            var newTime=""
            var anyChange=false
            for(task in taskList){
                findTaskCounter+=1
                println("searching for task ...")
                if (findTaskCounter==chosen){
                    println("task founded !")
                    println("--------------${task.key}--------------")
                    println("text = ${task.value[0]}")
                    println("date = ${task.value[1]} \ntime = ${task.value[2]}")
                    while(true){
                        print("change title (y/n) ? =")
                        var titleChose= readln()
                        if (titleChose == "n"){
                            newKey=task.key
                            oldKey=task.key
                            break
                        }
                        else if (titleChose == "y"){
                            print("new title =")
                            newKey= readln()
                            oldKey=task.key
                            anyChange=true
                            break
                        }
                    }
                    while(true){
                        print("change text (y/n) ? =")
                        var textChose= readln()
                        if (textChose == "n"){
                            newText=task.value[0]
                            break
                        }
                        else if (textChose == "y"){
                            print("new text =")
                            newText= readln()
                            anyChange=true
                            break
                        }
                    }

                    while(true){
                        print("change Date (y/n) ? =")
                        var dateChose= readln()
                        if (dateChose == "n"){
                            newDate=task.value[1]
                            break
                        }
                        else if (dateChose == "y"){
                            /*print("new date format (dd-mm-yyyy) =")
                            newDate= readln()
                            anyChange=true
                            break*/
                            while(true){
                                print("enter Date(format -> dd-mm-yyyy) =")
                                newDate=readln()
                                if(!newDate.isEmpty()){
                                    //thanks chatgpt for this part
                                    try {
                                        val pattern = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                                        var parsedDate = LocalDate.parse(newDate, pattern)
                                        break
                                    }
                                    catch (e: DateTimeParseException) {
                                        println("format ya date dorost vared nashode ast !")
                                    }
                                }
                            }
                            anyChange=true
                            break
                        }
                    }
                    while(true){
                        print("change Time (y/n) ? =")
                        var timeChose= readln()
                        if (timeChose == "n"){
                            newTime=task.value[2]
                            break
                        }
                        else if (timeChose == "y"){
                            /* print("new Time format (hh:mm:ss) =")
                            newTime= readln()
                            anyChange=true
                            break*/
                            while(true){
                                print("enter Time(format -> hh:mm:ss)=")
                                newTime=readln()
                                if(!newTime.isEmpty()) {
                                    //thanks chatgpt for this part too
                                    try{
                                        val pattern = DateTimeFormatter.ofPattern("HH:mm:ss")
                                        var parsedTime = LocalTime.parse(newTime,pattern)
                                        break
                                    }
                                    catch (e:DateTimeParseException){
                                        println("format ya time dorost vared nashode ast !")
                                    }
                                }
                            }
                            anyChange=true
                            break
                        }
                    }
                break
                }

            }
            //hala bayad hazf o jadidaro bezarim age taghir dashtim
            if(anyChange) {
                taskList.remove(oldKey)
                var TDTList = listOf<String>(newText, newDate, newTime)
                taskList.put(newKey, TDTList)
                println("task updated")
                // dobare bayad update she filemon
                var firstTime=true
                var content=""
                for(task in taskList){
                    if(firstTime){
                        content="${task.key}=${task.value[0]}=${task.value[1]}=${task.value[2]}"
                        firstTime=false}
                    else
                        content=content+"\n"+"${task.key}=${task.value[0]}=${task.value[1]}=${task.value[2]}"
                }
                var filePath="app/src/main/java/com/example/todolist/$username.txt"
                File(filePath).bufferedWriter().use { writer ->
                    writer.write(content)
                    println("Content has been written to the file.")
                }
            }

            print("press any key to back to TaskManager")
            readlnOrNull()
            print("\n\n\n\n\n\n\n\n\n")//insted of clear :)
            show()
        }
        if(chosenMenuInt == 4){
            var counter=1
            println("choose a task by its number")
            for (task in taskList){
                println("$counter => ${task.key}")
                counter+=1
            }
            var chooseRange=1..counter
            var chosen=0
            var sure=false
            while(true){
                print("which one ? =")
                chosen=readln().toInt()
                if(chosen in chooseRange){
                    while(true){
                        print("are you sure (y/n) =")
                        var chosenSure=readln()
                        if(chosenSure == "y"){
                            sure = true
                            break
                        }
                        else{break}
                    }
                    break
                }
            }
            var findTaskCounter=0
            var foundedkey=""
            for (task in taskList){
                findTaskCounter+=1
                if(findTaskCounter == chosen){
                    foundedkey=task.key
                    break
                }
            }
            if(sure) {
                taskList.remove(foundedkey)
                // dobare bayad update she filemon
                var firstTime=true
                var content=""
                for(task in taskList){
                    if(firstTime){
                        content="${task.key}=${task.value[0]}=${task.value[1]}=${task.value[2]}"
                        firstTime=false}
                    else
                        content=content+"\n"+"${task.key}=${task.value[0]}=${task.value[1]}=${task.value[2]}"
                }
                var filePath="app/src/main/java/com/example/todolist/$username.txt"
                File(filePath).bufferedWriter().use { writer ->
                    writer.write(content)
                    println("Content has been written to the file.")
                }
            }



            print("press any key to back to TaskManager")
            readlnOrNull()
            print("\n\n\n\n\n\n\n\n\n")//insted of clear :)
            show()
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