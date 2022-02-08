//Função que vai retornar o menu inicial
fun makeMenu(): String {
    return "\nWelcome to DEISI Minesweeper\n\n1 - Start New Game\n0 - Exit Game\n"
}

//Função que retorna "Enter player name?"
fun fraseNome() : String {
    return "Enter player name?"
}

const val esc = "\u001B" // ou Character.toString(27)
const val legendColor = "$esc[97;44m"
const val endLegendColor = "$esc[0m"

//Função que retorna "Show legend (y/n)?"
fun fraseLegend() : String {
    return "Show legend (y/n)?"
}



//Função que irá verificar se o nome introduzido pelo utilizador.
//(no mínimo, 2 nomes) (nomes devem estar separados por um espaço)
//(apenas e devem começar com a letra maiúscula e as restantes com letras minúsculas)
//(O primeiro nome tem que ter obrigatoriamente 3 letras)
fun isNameValid(name: String?, minLenght: Int = 3) : Boolean {
    var auxLenght = minLenght
    var auxContarMaiusculas = 1
    var auxContarEspacos = 0
    if (name != null) {
        if (name.length < auxLenght || name[0].isLowerCase() || !name[2].isLetter()) {
            return false
        }
        auxLenght = 1
        do {
            if(name[auxLenght].isLetter()) {
                if (auxContarEspacos == 1 && auxContarMaiusculas != 2) {
                    if (!name[auxLenght].isUpperCase()) {
                        return false
                    }
                    else {
                        auxContarMaiusculas++
                    }
                }
                else if (!name[auxLenght].isLowerCase()) {
                    return false
                }
            }
            else if (name[auxLenght].isWhitespace()) {
                auxContarEspacos++
            }
            else {
                return false
            }
            auxLenght++
        }while (auxLenght != name.length)
        if (auxContarEspacos != 1 || auxContarMaiusculas!=2) {
            return false
        }
        return true
    }

    return false

}

//Função que verifica se os números postos pelo utilizador estão corretos
fun isNumberValid(number : String): Boolean {

    var auxLenght = 0
    if (number.isEmpty()) {
        return false
    }
    do {
        if (!number[auxLenght].isDigit()) {
            return false
        }
        auxLenght++
    }while(auxLenght <= number.length - 1)

    return true
}


//Retorna verdadeiro caso o jogador tenha introduzido 0
fun quit(valor: String) : Boolean {
    if (valor == "0") {
        return true
    }
    return false
}

//Imprime "Invalid response" sempre que o utilizador colocar uma resposta que o programa nao está à espera
fun invalidResponse() : String {
    return "Invalid response.\n"
}

//Função que retorna "How many lines?"
fun fraseLinha() : String {
    return "How many lines?"
}

//Função que retorna "How many columns?"
fun fraseColunas() : String {
    return "How many columns?"
}

//Função que retorna "How many mines (press enter for default value)?"
fun fraseBombas() : String {
    return "How many mines (press enter for default value)?"
}

fun isLinhaValid(linha : Int) : Boolean {
    if (linha <4 || linha > 9) {
        return false
    }
    return true
}


//Função que verifica se as colunas introduzidas estão corretas
fun isColunaValid(coluna : Int) : Boolean {
    if (coluna<4 || coluna > 9) {
        return false
    }
    return true
}

//Função que verifica se as bombas introduzidas estão corretas
//𝑐𝑎𝑠𝑎_𝑣𝑎𝑧𝑖𝑎𝑠 = 𝑛ú𝑚𝑒𝑟𝑜_𝑑𝑒_𝑙𝑖𝑛h𝑎𝑠 * 𝑛ú𝑚𝑒𝑟𝑜_𝑑𝑒_𝑐𝑜𝑙𝑢𝑛𝑎𝑠 − 2
fun isBombasValid(bombas : Int , coluna : Int, linhas : Int) : Boolean {
    val casaVazias : Int = (linhas * coluna) - 2
    if (linhas < 1 || coluna<1) {
        return false
    }
    if (bombas in 1..casaVazias) {
        return true
    }
    return false
}


//Função que calcula o número de Minas em caso de "Enter"
fun calculateNumMinesForGameConfiguration(numLines: Int, numColumns: Int): Int? {
    if (numLines < 1 || numColumns < 1) {
        return null
    }
    val casaVazias : Int = (numLines * numColumns) - 2

    var minas=0
    if (casaVazias in 14..79) {
        when (casaVazias) {
            in 14..20 -> {
                minas = 6
            }
            in 21..40 -> {
                minas = 9
            }
            in 41..60 -> {
                minas = 12
            }
            in 61..79 -> {
                minas = 19
            }


        }
    }
    else {
        return null
    }
    return (minas)
}

//Verifica se o jogo foi feito corretamente
fun isValidGameMinesConfiguration(numLines: Int, numColumns: Int, numMines: Int): Boolean {
    val casaVazias : Int = (numLines * numColumns) - 2

    if (numMines <= 0 || casaVazias < numMines || numLines < 1 || numColumns < 1) {
        return false
    }
    return true
}

fun createMatrixTerrain(numLines:Int, numColumns: Int, numMines: Int,
                        ensurePathToWin: Boolean = false): Array<Array<Pair<String, Boolean>>> {

    val campo : Array<Array<Pair<String, Boolean>>> = Array(numLines) {Array(numColumns) {Pair(" " ,false )}  }
    var quadradoVolta : Pair<Pair<Int,Int>, Pair<Int, Int>>
    var minas = 0
    var centerX : Int
    var centerY : Int
    var yLeft : Int
    var xLeft : Int
    var xRight : Int
    var yRight : Int

    if (numLines < 1 || numColumns < 1) {
        return emptyArray()
    }

    campo[0][0]= Pair("P",true)
    campo[numLines-1][numColumns-1]= Pair("f",true)


    do {
        centerY = (0 until numLines).random()
        centerX = (0 until numColumns).random()


        if (!(centerX == 0 && centerY == 0) && (campo[centerY][centerX].first != "*") &&
            !(centerX == numColumns - 1 && centerY == numLines - 1)) {

            if (!ensurePathToWin) {
                campo[centerY][centerX] = Pair("*", false)
                minas++
            }
            else {
                quadradoVolta = getSquareAroundPoint(centerY, centerX, numLines, numColumns)

                yLeft = quadradoVolta.first.first
                xLeft = quadradoVolta.first.second
                yRight = quadradoVolta.second.first
                xRight = quadradoVolta.second.second


                if (!((yLeft == 0 && xLeft == 0) || ((yRight == numLines - 1) && (xRight == numColumns - 1)))) {
                    if (isEmptyAround(campo, centerY, centerX, yLeft, xLeft, yRight, xRight)) {
                        campo[centerY][centerX] = Pair("*", false)
                        minas++
                    }
                }
            }


        }
    }while (minas != numMines)

    return campo
}

fun getSquareAroundPoint(linha:Int, coluna: Int, numLines: Int, numColumns: Int): Pair<Pair<Int,Int>, Pair<Int, Int>> {

    if (numLines==1) {
        if (coluna!=0) {
            return Pair(Pair(0, coluna - 1), Pair(0,coluna+1))
        }
    }

    if ((linha == 0) || (coluna == 0) || (linha == numLines-1) || (coluna == numColumns-1)) {

        //Canto superior direito
        if ((linha == 0) && coluna == numColumns-1) {
            return Pair(Pair(linha, coluna-1), Pair(linha + 1, coluna))
        }

        //Canto inferior esquerdo
        else if ((coluna == 0) && linha == numLines-1) {

            return Pair(Pair(linha-1, coluna), Pair(linha, coluna+1))
        }
        else if (coluna == numColumns-1) {

            return Pair(Pair(linha-1, coluna-1), Pair(linha+1, coluna))

        }
        else if (coluna==0) {

            return Pair(Pair(linha-1, coluna), Pair(linha+1, coluna+1))

        }
        else if (linha==0) {

            return Pair(Pair(linha, coluna-1), Pair(linha+1, coluna+1))
        }

        else if (linha==numLines-1) {

            return Pair(Pair(linha-1, coluna-1), Pair(linha, coluna+1))
        }

    }

    return Pair(Pair(linha-1,coluna-1),Pair(linha+1,coluna+1))

}

fun isEmptyAround(matrixTerrain: Array<Array<Pair<String, Boolean>>>, centerY: Int, centerX: Int, yl: Int, xl: Int,
                  yr: Int, xr: Int): Boolean {
    val linha : Int = matrixTerrain.size-1
    val coluna : Int = matrixTerrain[linha].size-1

    //Novo
    if (yl < 0 || xl < 0 || yr > linha || xr > coluna) {
        return false
    }

    for (y: Int in yl..yr) {
        if (y <= linha) {
            for (x: Int in xl..xr) {
                if (x <= coluna) {
                    if (!(y == centerY && x == centerX)) {

                        if (matrixTerrain[y][x].first != " ") {
                            return false
                        }
                    }
                }
            }
        }
    }
    return true
}

fun fillNumberOfMines(matrixTerrain:Array<Array<Pair<String,Boolean>>>) {
    var nrBombas : Int
    if (matrixTerrain.isEmpty()) {
        return
    }

    for (linhas in matrixTerrain.indices) {
        for (colunas in 0 until matrixTerrain[linhas].size) {
            if (matrixTerrain[linhas][colunas].first == " ") {
                nrBombas = countNumberOfMinesCloseToCurrentCell(matrixTerrain,linhas,colunas)

                if (nrBombas!=0) {
                    matrixTerrain[linhas][colunas] = Pair(nrBombas.toString(), false)
                }
                else {
                    if (!matrixTerrain[linhas][colunas].second) {
                        matrixTerrain[linhas][colunas] = Pair(" ", false)
                    }
                    else {
                        matrixTerrain[linhas][colunas] = Pair(" ", true)

                    }
                }
            }
        }
    }
}

fun countNumberOfMinesCloseToCurrentCell(matrixTerrain:Array<Array<Pair<String,Boolean>>>,
                                         centerY: Int,centerX: Int): Int {

    var num = 0
    /*
    Defesa
    for (colunas: Int in centerX - 1..centerX + 1) {
        if (colunas <= matrixTerrain[centerY+1].size - 1 && colunas >= 0) {
            if (matrixTerrain[centerY+1][colunas].first == "*") {
                num++
            }
        }
    }

     */

    for (linhas:Int  in centerY-1..centerY+1) {
        if (linhas <= matrixTerrain.size-1 && linhas >=0) {
            for (colunas: Int in centerX - 1..centerX + 1) {
                if (colunas <= matrixTerrain[linhas].size - 1 && colunas >= 0) {
                    if (matrixTerrain[linhas][colunas].first == "*") {
                        num++
                    }
                }
            }
        }
    }

    return num
}


fun makeTerrain(matrixTerrain: Array<Array<Pair<String, Boolean>>>, showLegend: Boolean = true,
                withColor: Boolean = false, showEverything: Boolean): String {


    val textoFinalLegenda : String
    var numeroLinhas = 0
    var numeroColunas = 0

    var linhaVazia : String = if (!withColor) {
        "   "
    }
    else {
        "$legendColor   "
    }

    //Criação da linha vazia que se coloca em baixo do tabuleiro
    for (linhas in matrixTerrain.indices) {
        numeroLinhas++
        if (numeroColunas==0) {
            for (colunas in 0 until matrixTerrain[linhas].size) {
                linhaVazia += if (colunas == matrixTerrain[linhas].size) {
                    "   "
                } else {
                    "    "
                }

                numeroColunas++
            }
        }

    }
    textoFinalLegenda = criacaoTabuleiro(matrixTerrain,withColor,showLegend,numeroLinhas,numeroColunas,showEverything,linhaVazia)



    return textoFinalLegenda
}


fun criacaoTabuleiro(matrixTerrain: Array<Array<Pair<String, Boolean>>>, withColor: Boolean = false, showLegend: Boolean,
                     numeroLinhas: Int, nrColumns: Int, showEverything: Boolean, linhaVazia: String) : String{

    var textoColunas = ""
    var textoBaixo : String
    var textoFinalLegenda = ""


    if (numeroLinhas < 1 || nrColumns < 1) {
        return null.toString()
    }

    if (showLegend) {
        textoFinalLegenda = if (withColor) {
            "$legendColor    "
        }
        else {
            "    "

        }
        textoFinalLegenda += createLegend(nrColumns)
        textoFinalLegenda += if (withColor) {
            "    $endLegendColor\n"
        }
        else {
            "    \n"

        }
        //textoFinalLegenda = criarStringLegenda(textoLegenda,withColor)

        for (linha in matrixTerrain.indices) {
            textoColunas = legendaColuna(withColor,linha)
            textoBaixo = if (!withColor) {
                "   "
            } else {
                "$legendColor   $endLegendColor"

            }
            for (colunas in 0 until matrixTerrain[linha].size) {
                textoBaixo += stringTextoBaixo(colunas,nrColumns)
                textoColunas += stringPorLinha(showEverything,colunas,linha,matrixTerrain,matrixTerrain[linha][colunas])
            }
            if (numeroLinhas==1) {
                if (!withColor) {
                    return "$textoFinalLegenda$textoColunas   \n$linhaVazia  "
                }
            }

            textoBaixo += if (!withColor) {
                "   "
            } else {
                "$legendColor   $endLegendColor"
            }
            textoFinalLegenda += concatenarInfo(showLegend,numeroLinhas,withColor,textoColunas,linhaVazia,textoBaixo,linha)
        }


    }
    else {
        for (linha in matrixTerrain.indices) {
            textoBaixo = ""
            textoColunas = ""

            for (colunas in 0 until matrixTerrain[linha].size) {
                textoColunas += stringPorLinha(showEverything,colunas,linha,matrixTerrain,matrixTerrain[linha][colunas])
                textoBaixo += stringTextoBaixo(colunas, nrColumns)

            }
            textoFinalLegenda += concatenarInfo(showLegend,numeroLinhas,withColor,textoColunas,linhaVazia,textoBaixo,linha)

        }
        if (numeroLinhas==1) {
            textoFinalLegenda = textoColunas
        }
    }
    return textoFinalLegenda
}

//Função que irá criar uma legenda para o mapa que irá ser mostrado no ecrã (Não mexer mais)
fun createLegend(numColumns: Int): String {
    var aux = 0
    var textoApoio : String
    val alfabeto  ="A B C D E F G H I J K L M N O P Q R S T U V X Z"
    var auxiliar = numColumns
    var textoFinalLegenda = ""

    if (numColumns < 1) {
        return null.toString()
    }

    do{
        textoApoio = alfabeto[aux].toString()

        textoFinalLegenda += if (auxiliar!=1) {
            "$textoApoio   "
        } else {
            textoApoio
        }

        aux+=2

        auxiliar--
    }while (auxiliar!=0)

    return textoFinalLegenda
}


fun legendaColuna(withColor: Boolean = false, linha: Int): String {
    return if (withColor) {
        "$legendColor ${linha + 1} $endLegendColor"
    } else {
        " ${linha + 1} "
    }
}


fun stringTextoBaixo(colunas: Int, nrColunas: Int): String {
    var textoBaixo = ""

    textoBaixo += if (colunas != nrColunas-1) {
        "---+"

    } else {
        "---"
    }

    return textoBaixo
}

fun stringPorLinha(showEverything: Boolean, colunas: Int, linha: Int, matrixTerrain: Array<Array<Pair<String, Boolean>>>
                   , informacao: Pair<String, Boolean>): String {
    var textoColunas = ""

    if (showEverything) {
        textoColunas += if ((colunas == matrixTerrain[linha].size - 1)) {
            " ${informacao.first} "
        } else {

            " ${informacao.first} |"

        }
    } else {
        textoColunas += if ((colunas == matrixTerrain[linha].size - 1)) {
            if (informacao.second) {
                " ${informacao.first} "
            } else {
                "   "
            }
        } else {
            if (informacao.second) {
                " ${informacao.first} |"
            } else {
                "   |"
            }
        }
    }

    return textoColunas
}


fun concatenarInfo(showLegend: Boolean, numeroLinhas: Int, withColor: Boolean = false, textoColunas: String,
                   linhaVazia: String, textoBaixo: String, linha: Int): String {
    return  if (showLegend) {
        if (numeroLinhas != 1 && linha != numeroLinhas - 1) {
            if (!withColor) {
                "$textoColunas   \n$textoBaixo\n"
            } else {
                "$textoColunas$legendColor   $endLegendColor\n$textoBaixo\n"
            }

        } else {
            if (!withColor) {
                "$textoColunas   \n$linhaVazia  "
            } else {
                "$textoColunas$legendColor   $endLegendColor\n$linhaVazia  $endLegendColor"
            }

        }
    }
    else {
        if (numeroLinhas!=1 && linha != numeroLinhas-1) {
            if (textoBaixo.isNotBlank()) {
                "$textoColunas\n$textoBaixo\n"
            } else {
                "$textoColunas\n"
            }
        }else {
            textoColunas

        }
    }
}


fun getCoordinates (readText:String?): Pair<Int, Int>? {
    val abecedario = "ABCDEFGHIabcdefghi"
    val valores ="123456789"
    var linha = ""
    var coluna = ""
    if (readText==null || readText.length!=2){
        return null
    }
    if (valores.contains(readText[0]) && abecedario.contains(readText[1])) {
        linha += readText[0]
        when (readText[1]) {
            'A','a' -> {
                coluna += 0
            }
            'B','b' -> {
                coluna += 1
            }
            'C','c' -> {
                coluna += 2
            }
            'D','d' -> {
                coluna += 3
            }
            'E','e' -> {
                coluna += 4
            }
            'F','f' -> {
                coluna += 5
            }
            'G','g' -> {
                coluna += 6
            }
            'H','h' -> {
                coluna += 7
            }
            'I','i' -> {
                coluna += 8
            }
        }
    }
    else return null
    return Pair(Integer.parseInt(linha)-1 , Integer.parseInt(coluna))
}

fun isCoordinateInsideTerrain(coord:Pair<Int, Int>, numColumns: Int,numLines: Int): Boolean {

    if ((coord.first in 0 until numLines) && (coord.second in 0 until numColumns)) {
        return true
    }
    return false
}

fun isMovementPValid(currentCoord :Pair<Int, Int>, targetCoord :Pair<Int, Int>): Boolean {

    val currentY:Int = currentCoord.first
    val currentX :Int = currentCoord.second

    val targetY :Int = targetCoord.first
    val targetX :Int = targetCoord.second


    /*
    Defesa de nota
    if ((targetX>=currentX-1 && targetX<=currentX+1) && (targetY == currentY)) {
        return true
    }

     */

    if ((targetX>=currentX-1 && targetX<=currentX+1) && (targetY>=currentY-1 && targetY<= currentY+1)){
        return true
    }


    return false
}



fun revealMatrix(matrixTerrain:Array<Array<Pair<String,Boolean>>>, coordY: Int, coordX:Int, endGame: Boolean = false) {

    val numeroLinhas = matrixTerrain.size-1
    val numeroColunas = matrixTerrain[numeroLinhas].size-1

    if (matrixTerrain.isEmpty()) {
        return
    }

    if (!endGame) {
        //Ciclo for e depois divido a “string”
        for (linha: Int in coordY - 1..coordY + 1) {
            if (linha in 0..numeroLinhas) {
                for (coluna in coordX - 1..coordX + 1) {
                    if (coluna in 0..numeroColunas) {
                        if (matrixTerrain[linha][coluna].first != "*" && matrixTerrain[linha][coluna].first != "P") {
                            matrixTerrain[linha][coluna] = Pair(matrixTerrain[linha][coluna].first, true)

                        }
                    }
                }
            }
        }

    } else {
        //Ciclo for e depois divido a “string”
        for (linha: Int in coordY - 1..coordY + 1) {
            if (linha in 0..numeroLinhas) {
                for (coluna in coordX - 1..coordX + 1) {
                    if (coluna in 0..numeroColunas) {
                        matrixTerrain[linha][coluna] = Pair(matrixTerrain[linha][coluna].first, true)
                    }
                }
            }
        }
    }
}

fun parIgual(matrizAux: Array<Array<Pair<String, Boolean>>>, numeroLinhas: Int, numeroColunas: Int) : Pair<Int, Int>? {

    if (numeroColunas <= 0 || numeroLinhas <= 0) {
        return null
    }
    for (linhas:Int in 0..numeroLinhas) {
        for (colunas:Int in 0..numeroColunas) {
            if (matrizAux[linhas][colunas].first == "P") {
                if (!(linhas==0 && colunas==0)) {
                    return Pair(linhas, colunas)
                }
            }
        }
    }
    return Pair(0,0)
}


fun lerNome(): String? {
    println(fraseNome())
    val nome: String = readLine().toString()
    if (!isNameValid(nome, 3)) {
        println(invalidResponse())
        return null
    }

    return nome

}

fun lerLegenda(): Boolean? {
    println(fraseLegend())
    return when (readLine().toString()) {
        "y", "Y" -> {
            true
        }
        "n", "N" -> {
            false
        }
        else -> {
            println(invalidResponse())
            null
        }
    }

}

fun lerLinhas(): Int? {
    println(fraseLinha())
    val numeroLinhas = readLine()?.toIntOrNull() ?: -50
    if (!isLinhaValid(numeroLinhas)) {
        println(invalidResponse())
        return null
    }
    return numeroLinhas
}

fun lerColunas(): Int? {
    println(fraseColunas())
    val numeroColunas = readLine()?.toIntOrNull() ?: -50
    if (!isColunaValid(numeroColunas)) {
        println(invalidResponse())
        return null
    }
    return numeroColunas
}

fun lerBombas(numeroLinhas: Int, numeroColunas: Int): Int? {
    println(fraseBombas())
    val numeroBombas : Int
    var default = readLine().toString()
    if (default.isEmpty()) {
        default = calculateNumMinesForGameConfiguration(numeroLinhas, numeroColunas).toString()
        numeroBombas = default.toInt()
    } else {
        numeroBombas = default.toInt()
        if (!isBombasValid(numeroBombas, numeroColunas, numeroLinhas)) {
            println(invalidResponse())
            return null
        }
    }

    return numeroBombas
}

fun mostraCampo(currentPosition: Pair<Int, Int>, matriz: Array<Array<Pair<String, Boolean>>>, booleanLegend: Boolean) {

    matriz[currentPosition.first][currentPosition.second] = Pair("P", true)

    val mapaTerreno = makeTerrain(matriz, booleanLegend, withColor = false, showEverything = true)
    matriz[currentPosition.first][currentPosition.second]= Pair(
        matriz[currentPosition.first][currentPosition.second].first, true)
    println(mapaTerreno)
}

fun verificarMovimento(informacao: Pair<String, Boolean>, cordenadas: Pair<Int, Int>,
                       matriz: Array<Array<Pair<String, Boolean>>>, booleanLegend: Boolean): Boolean {
    val mapaTerreno: String
    val valores: Pair<Int, Int>
    var numeroLinhas = -1
    var numeroColunas = -1

    for (linhas in matriz.indices) {
        numeroLinhas++
        if (numeroLinhas == 1) {
            for (colunas in 0 until matriz[linhas].size) {
                numeroColunas++
            }
        }
    }

    if (informacao.first != "*" && informacao.first != "f") {
        fillNumberOfMines(matriz)
        valores = parIgual(matriz, numeroLinhas, numeroColunas)!!
        matriz[cordenadas.first][cordenadas.second] = Pair("P", true)
        revealMatrix(matriz, cordenadas.first, cordenadas.second, false)

        //Fazer a troca de valores , por o valor antigo
        if (!(valores.first == 0 && valores.second == 0)) {

            val numeroBombas = countNumberOfMinesCloseToCurrentCell(matriz, valores.first, valores.second)
            if (numeroBombas != 0) {
                matriz[valores.first][valores.second] = Pair("$numeroBombas", true)
            } else {
                matriz[valores.first][valores.second] = Pair(" ", true)

            }
        } else {
            matriz[valores.first][valores.second] = Pair(" ", true)

        }
        mapaTerreno = makeTerrain(matriz, booleanLegend, withColor = false, showEverything = false)
        println(mapaTerreno)
        return false

    } else {
        revealMatrix(matriz, cordenadas.first, cordenadas.second, true)

        mapaTerreno = makeTerrain(matriz, booleanLegend, withColor = false, showEverything = true)
        println(mapaTerreno)
        if (informacao.first == "*") {
            println("You lost the game!")
            return true
        } else if (informacao.first == "f") {
            println("You win the game!")
            return true
        }
        for (linhas in matriz.indices) {
            for (colunas in 0 until matriz[linhas].size) {
                if (informacao.first != "*" && informacao.first != "P") {
                    matriz[linhas][colunas] = Pair(informacao.first, true)
                }
            }

        }
        return false
    }
}

fun opcao(): Int {
    var aux = 0
    val menu: String = makeMenu()
    println(menu)
    val opcaoMenu: String = readLine().toString()
    if (isNumberValid(opcaoMenu)) {
        if (opcaoMenu == "1") {
            aux++
        } else if (quit(opcaoMenu)) {
            return aux
        } else {
            println(invalidResponse())
            aux--
        }
    } else {
        println(invalidResponse())
        aux--
    }
    return aux
}

fun receberValoresMapa(): Triple<Int, Int, Int> {
    var numeroLinhas: Int?
    var numeroColunas: Int?
    var numeroBombas: Int?
    do {
        numeroLinhas = lerLinhas()
    } while (numeroLinhas == null)
    do {
        numeroColunas = lerColunas()
    } while (numeroColunas == null)
    do {
        numeroBombas =  lerBombas(numeroLinhas,numeroColunas)
    } while (numeroBombas==null)

    return Triple(numeroLinhas,numeroColunas,numeroBombas)
}


fun main() {
    var auxOpcaoMenuValidado : Int
    var gameIsOver : Boolean?
    var exit = false
    var erro = false
    var booleanLegend: Boolean?
    var valores : Triple<Int,Int,Int>
    do {
        var help = 1

        do {
            auxOpcaoMenuValidado = opcao()
            if (auxOpcaoMenuValidado == 0) {
                return
            }
        } while (auxOpcaoMenuValidado != 1)
        do {
            val nome = lerNome()
        } while (nome==null)
        do {
            booleanLegend = lerLegenda()
        } while (booleanLegend == null)
        valores = receberValoresMapa()
        val matriz: Array<Array<Pair<String, Boolean>>> =
            createMatrixTerrain(valores.first, valores.second, valores.third, false)
        fillNumberOfMines(matriz)

        var mapaTerreno = makeTerrain(matriz, booleanLegend, withColor = false, showEverything = false)
        var currentPosition = Pair(0, 0)
        revealMatrix(matriz,0, 0, false)
        println(mapaTerreno)
        gameIsOver = false
        do {

            if (erro) {
                println(mapaTerreno)
                erro=false
            }

            println("$help remaining helps")
            println("${countNumberOfMinesBeyondCurrentCoords(matriz,currentPosition.first,currentPosition.second)} remaining mines until the end")

            println("Choose the Target cell (e.g 2D)")
            val input: String = readLine().toString()
            if (input == "abracadabra") {
                mostraCampo(currentPosition,matriz,booleanLegend)
            } else if (input == "help" && help > 0) {
                revealOneMine(matriz)
                mapaTerreno = makeTerrain(matriz, booleanLegend, withColor = false, showEverything = false)
                println(mapaTerreno)
                help--
            } else if (input == "exit") {
                exit = true
                gameIsOver = true
            } else {
                val cordenadas: Pair<Int, Int>? = getCoordinates(input)
                if (cordenadas==null) {
                    print(invalidResponse())
                    erro=true
                    mapaTerreno = makeTerrain(matriz, booleanLegend, withColor = false, showEverything = false)
                }
                else {
                    if ((isCoordinateInsideTerrain(cordenadas, valores.second, valores.first)) &&
                        (isMovementPValid(currentPosition, cordenadas))) {
                        if (matriz[cordenadas.first][cordenadas.second].first!="*"
                            && matriz[cordenadas.first][cordenadas.second].first!="f") {
                            currentPosition = cordenadas
                        }
                        gameIsOver = verificarMovimento(matriz[cordenadas.first][cordenadas.second],cordenadas,matriz,booleanLegend)
                    }
                    else {
                        print(invalidResponse())
                        mapaTerreno = makeTerrain(matriz, booleanLegend, withColor = false, showEverything = false)
                        erro = true
                    }
                }
            }
        } while (gameIsOver == false)
    }while (!exit)

    return
}



fun countNumberOfMinesBeyondCurrentCoords(matrixTerrain: Array<Array<Pair<String,Boolean>>>, coordY: Int, coordX: Int): Int {

    var numeroBombas= 0
    if (coordX<0 || coordY<0) {
        return 0
    }
    for (linhas in coordY until matrixTerrain.size) {
        for (colunas in coordX until matrixTerrain[linhas].size) {
            if (matrixTerrain[linhas][colunas].first == "*") {
                numeroBombas++
            }
        }
    }
    return numeroBombas
}

fun revealOneMine(matrixTerrain: Array<Array<Pair<String, Boolean>>>) {
    for (linhas in matrixTerrain.indices) {
        for (colunas in  0 until matrixTerrain[linhas].size) {
            if (matrixTerrain[linhas][colunas].first == "*" && !matrixTerrain[linhas][colunas].second) {
                matrixTerrain[linhas][colunas] = Pair(matrixTerrain[linhas][colunas].first,true)
                return
            }
        }
    }
}


//Função para testes
/*
fun main() {
    val matriz :Array<Array<Pair<String,Boolean>>> = createMatrixTerrain(2, 7, 1, true)
    //fillNumberOfMines(matriz)
    val mapaTerreno = makeTerrain(matriz,  true, withColor = true, showEverything = false)

    println(mapaTerreno)



    /*
    println(isEmptyAround(matriz,0,4,0,3,0,4))
    println(isEmptyAround(matriz,0,4,0,3,0,4))

     */


/*
    val matriz :Array<Array<Pair<String,Boolean>>> = Array(5) {Array(5) {Pair(" " ,false )}  }

    matriz[0][0] = Pair("P",true)
    matriz[0][1] = Pair("*",false)
    matriz[0][2] = Pair(" ",false)
    matriz[0][3] = Pair(" ",true)
    matriz[0][4] = Pair(" ",false)

    matriz[1][0] = Pair(" ",false)
    matriz[1][1] = Pair(" ",false)
    matriz[1][2] = Pair(" ",false)
    matriz[1][3] = Pair(" ",false)
    matriz[1][4] = Pair(" ",false)

    matriz[2][0] = Pair(" ",false)
    matriz[2][1] = Pair(" ",false)
    matriz[2][2] = Pair("*",false)
    matriz[2][3] = Pair(" ",false)
    matriz[2][4] = Pair(" ",false)

    matriz[3][0] = Pair(" ",false)
    matriz[3][1] = Pair(" ",false)
    matriz[3][2] = Pair(" ",false)
    matriz[3][3] = Pair(" ",false)
    matriz[3][4] = Pair(" ",false)

    matriz[4][0] = Pair(" ",false)
    matriz[4][1] = Pair(" ",false)
    matriz[4][2] = Pair(" ",false)
    matriz[4][3] = Pair(" ",false)
    matriz[4][4] = Pair("f",true)

    println(isEmptyAround(matriz,3,3,2,2,4,5))

 */


/*
    fillNumberOfMines(matriz)



        /*
        println(isEmptyAround(matriz,3,3,2,2,4,4))

        fillNumberOfMines(matriz)


        println(countNumberOfMinesCloseToCurrentCell(matriz,0,0))
        println(countNumberOfMinesCloseToCurrentCell(matriz,0,1))
        println(countNumberOfMinesCloseToCurrentCell(matriz,0,2))
        println(countNumberOfMinesCloseToCurrentCell(matriz,0,3))
        println(countNumberOfMinesCloseToCurrentCell(matriz,0,4))
        println(countNumberOfMinesCloseToCurrentCell(matriz,1,0))
        println(countNumberOfMinesCloseToCurrentCell(matriz,1,1))
        println(countNumberOfMinesCloseToCurrentCell(matriz,1,2))
        println(countNumberOfMinesCloseToCurrentCell(matriz,1,3))
        println(countNumberOfMinesCloseToCurrentCell(matriz,1,4))

         */
    /*
    println(matriz[0][0])
    revealMatrix(matriz, 1, 1, false)
    println(matriz[0][0])



    revealMatrix(matriz, 0, 1, true)
    println(matriz[0][0])

     */




    var mapaTerreno = makeTerrain(matriz, true, withColor = true, showEverything = true)
    println(mapaTerreno)





    for (linhas:Int in matriz.indices) {
        for (colunas:Int in 0 until  matriz[linhas].size){
            println("($linhas,$colunas) = ${matriz[linhas][colunas]}")
        }
    }



    //println(mapaTerreno)

 */

}


 */
















/*
fun criarStringLegenda(textoLegenda: String, show: Boolean): String {
    var textoFinalLegenda : String = if (show) {
        "$legendColor   "
    }
    else {
        "   "

    }
    var tamanho = 0

    textoFinalLegenda += " "
    do {

        textoFinalLegenda += textoLegenda[tamanho]
        tamanho += 4
        if (tamanho < textoLegenda.length) {
            textoFinalLegenda += "   "
        }

    }while (tamanho < textoLegenda.length)

    textoFinalLegenda += if (show) {
        "    $endLegendColor\n"
    }
    else {
        "  \n"

    }

    return textoFinalLegenda

}

 */
