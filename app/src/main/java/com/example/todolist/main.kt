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
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.nio.file.Path
import kotlin.concurrent.thread

//var accessList= mutableListOf("loginSignup","taskMenu")
//var accessList= mutableListOf("loginSignup")
var accessList= mutableListOf("taskMenu")
var taskList= HashMap<String,List<String>>()
var showList= HashMap<String,MutableList<String>>()
@TargetApi(Build.VERSION_CODES.O)
class ConsoleView(var accessList:List<String>, var username :String?=null){
    var accessMap= HashMap<Int,String>()
    var listCounter=0
    val notification= Thread{
        var findTasksObj=TaskManagerClass(username)
        findTasksObj.findTaskList()
        var showChoose=false
        while(true){
            Thread.sleep(400)
            var localDate=LocalDate.now()
            var localTime=LocalTime.now()
            val formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss")
            var formattedDate=localDate.format(formatterDate)
            var formattedTime=localTime.format(formatterTime)
            for (task in taskList){
                var taskDate=task.value[1]
                var taskTime=task.value[2]
                if( taskDate==formattedDate && taskTime==formattedTime){
                    //inja yani vaght ye task shode
                        if(!showList.containsKey(task.key)) {
                            var toShowList = mutableListOf<String>(task.value[0], task.value[1], task.value[2], "no")
                            showList.put(task.key, toShowList)}
                            for (show in showList) {
                                if(show.value[3] == "no") {

                                    println("\n----------notification !------------")
                                    println("its time to do ${show.key}")
                                    println("date = ${show.value[1]} time=${show.value[2]}\n")
                                    show.value[3]="yes"
                                    showChoose=true

                                }

                            }


                }
            }

            if(showChoose){
                showChoose=false
                println("1111111111111")
                println("choose section")
                //now check our access + put access to access map
                for (access in accessList) {
                    println("222222222")

                    println("$listCounter - $access")
                }
                println("33333333333333")
                print("choosen Menu =")

            }
        }
    }
    fun menu(){
            try{notification.start()}
            catch (e: IllegalThreadStateException){
            }

            var chosenMenu = 0
            var menuRange = false
            println("choose section")
            //now check our access + put access to access map
            for (access in accessList) {
                listCounter += 1
                accessMap.put(listCounter, access)
                println("$listCounter - $access")

            }
            while (!menuRange) {
                //waits for user choose
                try {
                    print("choosen Menu =")
                    chosenMenu = readln().toInt()
                    var menuRangeRange = 1..listCounter
                    if (chosenMenu in menuRangeRange) break
                } catch (e: NumberFormatException) {
                }
            }

            try{notification.stop()
            println("i stopped it")
            }
            catch (e:IllegalThreadStateException){
                println("erroorooro")
            }
            chosenMenuNavigator(chosenMenu, accessMap)


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
            try{
                print("choose menu=")
                chosenMenuInt = readln().toInt()
                if(chosenMenuInt in rangeMenu)break
            }
            catch (e:NumberFormatException){}
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
                try {
                    print("which one ? =")
                    chosen = readln().toInt()
                    if (chosen in chooseRange) break
                }
                catch (e:NumberFormatException){}

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
                try {
                    print("which one ? =")
                    chosen = readln().toInt()

                    if (chosen in chooseRange) {
                        while (true) {
                            print("are you sure (y/n) =")
                            var chosenSure = readln()
                            if (chosenSure == "y") {
                                sure = true
                                break
                            } else {
                                break
                            }
                        }
                        break
                    }
                }
                catch (e:NumberFormatException){}

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
            try {
                print("your choice =")
                chosenMenuIntLS = readln().toInt()
                if(chosenMenuIntLS == 1 || chosenMenuIntLS ==2)menuRange=true
            }
            catch (e:NumberFormatException){}

        }
        chosenLS(chosenMenuIntLS)

    }
    @TargetApi(Build.VERSION_CODES.O)
    fun chosenLS(chosen:Int){
        //inja bayad list membrea + pass begire az file
        var userList=HashMap<String,String>()
        val lines = Files.readAllLines(Paths.get("app/src/main/java/com/example/todolist/user.txt"))
        for (line in lines){
            var userPassArray=line.split("=")
            userList.put(userPassArray[0],userPassArray[1])
        }
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

            var userExist=false
            for(user in userList){
                if(username == user.key){
                    userExist=true
                }
            }

            if(!userExist)userList.put(username,password)
            else println("user exist")
            while(true){
                print("enter any key to leave=")
                var breaker= readln()
                if(breaker !="")break
            }

            var content=""
            var firstTime=true
            for(user in userList){
                if(firstTime){
                    content="${user.key}=${user.value}"
                    firstTime=false
                }
                else{
                    content+="\n"+"${user.key}=${user.value}"
                }
            }
            var filePath="app/src/main/java/com/example/todolist/user.txt"
            File(filePath).bufferedWriter().use { writer ->
                writer.write(content)
                println("Content has been written to the file.")
            }
            //inja bayad barash ye file bsazim ta betone save kone tosh
            val filePathForFile = "app/src/main/java/com/example/todolist/$username.txt"
            val file = File(filePathForFile)
            file.createNewFile()
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