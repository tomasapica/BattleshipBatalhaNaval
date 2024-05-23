var numLinhas = -1
var numColunas = -1
var verificacao = false

var tabuleiroHumano: Array<Array<Char?>> = emptyArray()
var tabuleiroComputador: Array<Array<Char?>> = emptyArray()

var tabuleiroPalpitesDoHumano: Array<Array<Char?>> = emptyArray()
var tabuleiroPalpitesDoComputador: Array<Array<Char?>> = emptyArray()


fun tamanhoTabuleiroValido(numLinhas: Int?, numColunas: Int?): Boolean {

    if (numLinhas == 0 || numColunas == 0) return false

    return if ((numLinhas != null && numColunas != null) && (numLinhas == numColunas)) {
        when (true) {
            (numLinhas == 4) -> true
            (numLinhas == 5) -> true
            (numLinhas == 7) -> true
            (numLinhas == 8) -> true
            (numLinhas == 10) -> true
            else -> false
        }
    } else {
        false
    }
}


fun processaCoordenadas(coordenadas: String, numLinhas: Int, numColunas: Int): Pair<Int,Int>? {

    if (coordenadas.isEmpty() || coordenadas.length !in 3..4 || coordenadas[coordenadas.length - 2] != ',') {
        return null
    }

    val partes = coordenadas.split(',')

    val linha = if (partes[0].toIntOrNull() != null) {
        partes[0].toInt()
    } else {
        return null
    }
    val coluna = partes[1][0].code - 64

    if (linha !in 1..numLinhas) return null
    if (coluna !in 1..numColunas) return null

    return Pair(linha,coluna)
}


fun criaLegendaHorizontal(numColunas: Int?): String {

    if (numColunas == -1) return ""

    var letra = 65
    var legenda = ""
    var repeticoes = 0

    if(numColunas == null) return ""

    return if(numColunas !in 1..26) {
        ""
    } else {
        while(numColunas > repeticoes) {
            repeticoes++
            legenda += "${letra.toChar()}"
            letra++
            if (numColunas != repeticoes) legenda += " | "
        }
        legenda
    }
}


fun calculaNumNavios(numLinhas: Int?, numColunas: Int?): Array<Int> {

    if (numLinhas == null || numColunas == null) {
        return emptyArray()
    }
    return if (!tamanhoTabuleiroValido(numLinhas,numColunas)) {
        emptyArray()
    } else {
        when (numLinhas) {
            4 -> arrayOf(2,0,0,0)
            5 -> arrayOf(1,1,1,0)
            7 -> arrayOf(2,1,1,1)
            8 -> arrayOf(2,2,1,1)
            10 -> arrayOf(3,2,1,1)
            else -> emptyArray()
        }
    }
}


fun criaTabuleiroVazio(numLinhas: Int?, numColunas: Int?): Array<Array<Char?>> {

    if (numLinhas == null || numColunas == null) return arrayOf(arrayOfNulls(0))

    return Array(numLinhas) {arrayOfNulls(numColunas)}
}


fun coordenadaContida(tabuleiro: Array<Array<Char?>>, linha: Int?, coluna: Int?): Boolean {

    if (linha == null && coluna == null) return false

    return linha in 1..tabuleiro.size && coluna in 1..tabuleiro[0].size
}


fun limparCoordenadasVazias(coordenadas: Array<Pair<Int, Int>>): Array<Pair<Int, Int>> {

    var copia: Array<Pair<Int,Int>> = Array(0) {Pair(0,0)}
    for (posicao in 0 until coordenadas.size){
        if (coordenadas[posicao] != Pair(0,0)) {
            copia += coordenadas[posicao]
        }
    }
    return copia
}


fun juntarCoordenadas(coordenadas1: Array<Pair<Int,Int>>, coordenadas2: Array<Pair<Int,Int>>): Array<Pair<Int,Int>> {

    var soma : Array<Pair<Int,Int>> = Array(0) {Pair(0,0)}
    for (posicao in coordenadas1) {
        soma += posicao
    }
    for (posicao in coordenadas2) {
        soma += posicao
    }
    return soma
}


fun gerarCoordenadasNavio(tabuleiro: Array<Array<Char?>>, linha: Int?, coluna: Int?,
                          orientacao: String?, dimensao: Int?): Array<Pair<Int,Int>> {

    if (linha == null || coluna == null || orientacao == null || dimensao == null) {
        return emptyArray()
    }
    if (!coordenadaContida(tabuleiro,linha,coluna)) {
        return emptyArray()
    }
    var coordenadasNavio : Array<Pair<Int,Int>> = arrayOf(Pair(0,0))

    when (orientacao) {
        "E" -> when (dimensao) {
            1 -> if (coluna in 1..tabuleiro[0].size) {
                coordenadasNavio = arrayOf(Pair(linha,coluna))
            } else return emptyArray()

            2 -> if (coluna + 1 in 1..tabuleiro[0].size) {
                coordenadasNavio = arrayOf(Pair(linha, coluna), Pair(linha, coluna + 1))
            } else return emptyArray()

            3 -> if (coluna + 2 in 1..tabuleiro[0].size) {
                coordenadasNavio = arrayOf(Pair(linha, coluna), Pair(linha, coluna + 1), Pair(linha, coluna + 2))
            } else return emptyArray()

            4 -> if (coluna + 3 in 1..tabuleiro[0].size) {
                coordenadasNavio = arrayOf(Pair(linha, coluna), Pair(linha, coluna + 1), Pair(linha, coluna + 2), Pair(linha, coluna + 3))
            } else return emptyArray()
        }
        "O" -> when (dimensao) {
            1 -> if (coluna in 1..tabuleiro[0].size) {
                coordenadasNavio = arrayOf(Pair(linha,coluna))
            } else return emptyArray()

            2 -> if (coluna - 1 in 1..tabuleiro[0].size) {
                coordenadasNavio = arrayOf(Pair(linha, coluna - 1), Pair(linha, coluna))
            } else return emptyArray()

            3 -> if (coluna - 2 in 1..tabuleiro[0].size) {
                coordenadasNavio = arrayOf(Pair(linha, coluna - 2), Pair(linha, coluna - 1), Pair(linha, coluna))
            } else return emptyArray()

            4 -> if (coluna - 3 in 1..tabuleiro[0].size) {
                coordenadasNavio = arrayOf(Pair(linha, coluna - 3), Pair(linha, coluna - 2), Pair(linha, coluna - 1), Pair(linha, coluna))
            } else return emptyArray()
        }
        "N" -> when (dimensao) {
            1 -> if (coluna in 1..tabuleiro[0].size) {
                coordenadasNavio = arrayOf(Pair(linha,coluna))
            } else return emptyArray()

            2 -> if (linha - 1 in 1..tabuleiro[0].size) {
                coordenadasNavio = arrayOf(Pair(linha - 1, coluna), Pair(linha, coluna))
            } else return emptyArray()

            3 -> if (linha - 2 in 1..tabuleiro[0].size) {
                coordenadasNavio = arrayOf(Pair(linha - 2, coluna), Pair(linha - 1, coluna), Pair(linha, coluna))
            } else return emptyArray()

            4 -> if (linha - 3 in 1..tabuleiro[0].size) {
                coordenadasNavio = arrayOf(Pair(linha - 3, coluna), Pair(linha - 2, coluna), Pair(linha - 1, coluna), Pair(linha, coluna))
            } else return emptyArray()
        }
        "S" -> when (dimensao) {
            1 -> if (coluna in 1..tabuleiro[0].size) {
                coordenadasNavio = arrayOf(Pair(linha,coluna))
            } else return emptyArray()

            2 -> if (linha + 1 in 1..tabuleiro[0].size) {
                coordenadasNavio = arrayOf(Pair(linha, coluna), Pair(linha + 1, coluna))
            } else return emptyArray()

            3 -> if (linha + 2 in 1..tabuleiro[0].size) {
                coordenadasNavio = arrayOf(Pair(linha, coluna), Pair(linha + 1, coluna), Pair(linha + 2, coluna))
            } else return emptyArray()

            4 -> if (linha + 3 in 1..tabuleiro[0].size) {
                coordenadasNavio = arrayOf(Pair(linha, coluna), Pair(linha + 1, coluna), Pair(linha + 2, coluna), Pair(linha + 3, coluna))
            } else return emptyArray()
        }
        else -> return emptyArray()
    }
    return coordenadasNavio
}


fun gerarCoordenadasFronteira(tabuleiro: Array<Array<Char?>>, linha: Int?, coluna: Int?,
                              orientacao: String?, dimensao: Int?): Array<Pair<Int,Int>> {

    if (linha == null || coluna == null || orientacao == null || dimensao == null) {
        return emptyArray()
    }
    if (!coordenadaContida(tabuleiro,linha,coluna)) {
        return emptyArray()
    }
    var coordenadasECoordenadasFronteira : Array<Pair<Int,Int>> = emptyArray()
    val coordenadas = gerarCoordenadasNavio(tabuleiro,linha,coluna,orientacao,dimensao)
    for (posicao in 0 until coordenadas.size) {
        coordenadasECoordenadasFronteira = juntarCoordenadas(arrayOf(Pair(coordenadas[posicao].first + 1,coordenadas[posicao].second),
            Pair(coordenadas[posicao].first + 1,coordenadas[posicao].second + 1),
            Pair(coordenadas[posicao].first,coordenadas[posicao].second + 1),
            Pair(coordenadas[posicao].first - 1,coordenadas[posicao].second + 1),
            Pair(coordenadas[posicao].first - 1,coordenadas[posicao].second),
            Pair(coordenadas[posicao].first - 1,coordenadas[posicao].second - 1),
            Pair(coordenadas[posicao].first,coordenadas[posicao].second - 1),
            Pair(coordenadas[posicao].first + 1,coordenadas[posicao].second - 1)),
            coordenadasECoordenadasFronteira)
    }
    coordenadasECoordenadasFronteira = limparCoordenadasVazias(coordenadasECoordenadasFronteira)
    var coordenadasFronteiraDentro : Array<Pair<Int,Int>> = emptyArray()
    for (posicao in 0 until coordenadasECoordenadasFronteira.size){
        if(coordenadasECoordenadasFronteira[posicao].first in 1..tabuleiro.size
            && coordenadasECoordenadasFronteira[posicao].second in 1..tabuleiro[0].size){
            coordenadasFronteiraDentro += coordenadasECoordenadasFronteira[posicao]
        }
    }
    var coordenadasFronteira : Array<Pair<Int,Int>> = emptyArray()
    for(posicaoFronteira in 0 until coordenadasFronteiraDentro.size) {
        if (coordenadasFronteiraDentro[posicaoFronteira] !in coordenadas) {
            coordenadasFronteira += coordenadasFronteiraDentro[posicaoFronteira]
        }
    }
    return coordenadasFronteira
}




fun estaLivre(tabuleiro: Array<Array<Char?>>, coordenadas: Array<Pair<Int,Int>>): Boolean {

    for (posicao in 0 until coordenadas.size) {
        val linha = coordenadas[posicao].first - 1
        val coluna = coordenadas[posicao].second - 1

        if (linha !in 0 until tabuleiro.size && coluna !in 0 until tabuleiro[0].size) {
            return false
        }
        if (tabuleiro[linha][coluna] != null) {
            return false
        }
    }
    return true
}


fun insereNavioSimples(tabuleiro: Array<Array<Char?>>, linha: Int?, coluna: Int?, dimensao: Int?): Boolean {

    if (linha == null || coluna == null || dimensao == null) {
        return false
    }
    val coordenadasNavio = gerarCoordenadasNavio(tabuleiro,linha,coluna,"E",dimensao)
    val coordenadasFronteira = gerarCoordenadasFronteira(tabuleiro,linha,coluna,"E",dimensao)
    val coordenadasLivres = juntarCoordenadas(coordenadasNavio,coordenadasFronteira)
    var verificacao = false
    tabuleiroHumano = tabuleiro
    val navios = (dimensao + 48).toChar()
    if (estaLivre(tabuleiro,coordenadasLivres)) {
        for (posicao in 0 until coordenadasNavio.size) {
            tabuleiro[coordenadasNavio[posicao].first - 1][coordenadasNavio[posicao].second - 1] = navios
            verificacao = true
        }
    }
    tabuleiroHumano = tabuleiro
    return verificacao
}


fun insereNavio(tabuleiro: Array<Array<Char?>>, linha: Int?, coluna: Int?,
                orientacao: String?, dimensao: Int?): Boolean {

    if (linha == null || coluna == null || orientacao == null || dimensao == null) {
        return false
    }

    val coordenadasNavio = gerarCoordenadasNavio(tabuleiro,linha,coluna,orientacao,dimensao)
    val coordenadasFronteira = gerarCoordenadasFronteira(tabuleiro,linha,coluna,orientacao,dimensao)
    val coordenadasLivres = juntarCoordenadas(coordenadasNavio,coordenadasFronteira)
    val navios = (dimensao + 48).toChar()
    var verificacao = false
    tabuleiroHumano = tabuleiro

    when (orientacao) {
        "E" -> if (insereNavioSimples(tabuleiro,linha,coluna,dimensao)) {
            for (posicao in 0 until coordenadasNavio.size) {
                tabuleiro[coordenadasNavio[posicao].first - 1][coordenadasNavio[posicao].second - 1] = navios
                verificacao = true
            }
        }
        "O","N","S" -> if (estaLivre(tabuleiro,coordenadasLivres)) {
            for (posicao in 0 until coordenadasNavio.size){
                tabuleiro[coordenadasNavio[posicao].first - 1][coordenadasNavio[posicao].second - 1] = navios
                verificacao = true
            }
        }
        else -> verificacao = false
    }
    tabuleiroHumano = tabuleiro
    return verificacao
}


fun preencheTabuleiroComputador(tabuleiroVazio: Array<Array<Char?>>, configuracaoJogo: Array<Int>) {

    numLinhas = tabuleiroVazio.size
    numColunas = tabuleiroVazio[0].size

    var linha: Int
    var coluna: Int
    val direcoesOrientacao = arrayOf("N","S","E","O")
    var orientacao: String
    var dimensao = 4

    for (repeteNavio in configuracaoJogo.size - 1 downTo 0) {

        when (repeteNavio) {
            3 -> dimensao = 4
            2 -> dimensao = 3
            1 -> dimensao = 2
            0 -> dimensao = 1
        }

        if (configuracaoJogo[repeteNavio] != 0) {
            for (posicao in 1..configuracaoJogo[repeteNavio]) {
                do {
                    linha = (1..numLinhas).random()
                    coluna = (1..numColunas).random()
                    orientacao = direcoesOrientacao[(0..3).random()]
                } while (!insereNavio(tabuleiroVazio,linha,coluna,orientacao,dimensao))
            }
        }
    }
}


fun navioCompleto(tabuleiroPalpites: Array<Array<Char?>>, linha: Int?, coluna: Int?): Boolean {

    if (linha != null && coluna != null && linha in 1..tabuleiroPalpites.size &&
        coluna in 1..tabuleiroPalpites[0].size) {

        when (tabuleiroPalpites[linha - 1][coluna - 1]) {
            '1' -> return true

            '2' -> return tabuleiroPalpites[linha][coluna - 1] == '2' ||
                    tabuleiroPalpites[linha - 2][coluna - 1] == '2' ||
                    tabuleiroPalpites[linha - 1][coluna] == '2' ||
                    tabuleiroPalpites[linha - 1][coluna - 2] == '2'

            '3' -> return tabuleiroPalpites[linha][coluna - 1] == '3' ||
                    tabuleiroPalpites[linha - 2][coluna - 1] == '3' ||
                    tabuleiroPalpites[linha - 1][coluna] == '3' ||
                    tabuleiroPalpites[linha - 1][coluna - 2] == '3'

            '4' -> return tabuleiroPalpites[linha][coluna - 1] == '4' ||
                    tabuleiroPalpites[linha - 2][coluna - 1] == '4' ||
                    tabuleiroPalpites[linha - 1][coluna] == '4' ||
                    tabuleiroPalpites[linha - 1][coluna - 2] == '4'
        }
    }
    return false
}


fun obtemMapa(tabuleiro: Array<Array<Char?>>, verificacao: Boolean): Array<String?> {

    val legenda = criaLegendaHorizontal(tabuleiro[0].size)
    var terreno : Array<String?> = arrayOf("| $legenda |")
    var linhas = 1

    if (tabuleiro[0].size !in 1..26) {
        return arrayOf("")
    } else {
        while (linhas <= tabuleiro.size) {
            var texto = ""
            var repeticoesColuna = 0
            while (repeticoesColuna <= tabuleiro[0].size){
                repeticoesColuna++
                texto += if (repeticoesColuna != tabuleiro[linhas - 1].size + 1) {
                    if (tabuleiro[linhas - 1][repeticoesColuna - 1] != null) {
                        if (verificacao) {
                            "| ${tabuleiro[linhas - 1][repeticoesColuna - 1]} "
                        } else {
                            when (tabuleiro[linhas - 1][repeticoesColuna - 1]) {
                                '1' -> "| 1 "
                                '2' -> if (!navioCompleto(tabuleiro,linhas - 1,repeticoesColuna - 1)) {
                                    "| \u2082 "
                                } else {
                                    "| 2 "
                                }
                                '3' -> if (!navioCompleto(tabuleiro,linhas - 1,repeticoesColuna - 1)) {
                                    "| \u2083 "
                                } else {
                                    "| 3 "
                                }
                                '4' -> if (!navioCompleto(tabuleiro,linhas - 1,repeticoesColuna - 1)) {
                                    "| \u2084 "
                                } else {
                                    "| 4 "
                                }
                                else -> "| X "
                            }
                        }
                    } else {
                        if (verificacao) {
                            "| ~ "
                        } else {
                            "| ? "
                        }
                    }
                } else {
                    "| $linhas"
                }
            }
            terreno += texto
            linhas++
        }
    }
    return terreno
}


fun lancarTiro(tabuleiroReal: Array<Array<Char?>>, tabuleiroPalpites: Array<Array<Char?>>,
               coordenadas: Pair<Int?,Int?>): String {

    if (coordenadas.first !in 1..tabuleiroReal.size && coordenadas.second !in 1..tabuleiroReal[0].size) {
        return ""
    }

    if (coordenadas.first != null && coordenadas.second != null) {
        if (tabuleiroReal[coordenadas.first!! - 1][coordenadas.second!! - 1] == null) {
            if (tabuleiroPalpites[coordenadas.first!! - 1][coordenadas.second!! - 1] == null) {
                tabuleiroPalpites[coordenadas.first!! - 1][coordenadas.second!! - 1] = 'X'
            }
            return "Agua."
        }
        if (tabuleiroReal[coordenadas.first!! - 1][coordenadas.second!! - 1] == '1') {
            if (tabuleiroPalpites[coordenadas.first!! - 1][coordenadas.second!! - 1] == null) {
                tabuleiroPalpites[coordenadas.first!! - 1][coordenadas.second!! - 1] = '1'
            }
            return "Tiro num submarino."
        }
        if (tabuleiroReal[coordenadas.first!! - 1][coordenadas.second!! - 1] == '2') {
            if (tabuleiroPalpites[coordenadas.first!! - 1][coordenadas.second!! - 1] == null) {
                tabuleiroPalpites[coordenadas.first!! - 1][coordenadas.second!! - 1] = '2'
            }
            return "Tiro num contra-torpedeiro."
        }
        if (tabuleiroReal[coordenadas.first!! - 1][coordenadas.second!! - 1] == '3') {
            if (tabuleiroPalpites[coordenadas.first!! - 1][coordenadas.second!! - 1] == null) {
                tabuleiroPalpites[coordenadas.first!! - 1][coordenadas.second!! - 1] = '3'
            }
            return "Tiro num navio-tanque."
        }
        if (tabuleiroReal[coordenadas.first!! - 1][coordenadas.second!! - 1] == '4') {
            if (tabuleiroPalpites[coordenadas.first!! - 1][coordenadas.second!! - 1] == null) {
                tabuleiroPalpites[coordenadas.first!! - 1][coordenadas.second!! - 1] = '4'
            }
            return "Tiro num porta-avioes."
        }
    }
    return ""
}


fun geraTiroComputador(tabuleiroPalpitesComputador: Array<Array<Char?>>): Pair<Int,Int> {

    numLinhas = tabuleiroPalpitesComputador.size
    numColunas = tabuleiroPalpitesComputador[0].size

    var linha = (1..numLinhas).random()
    var coluna = (1..numColunas).random()

    while (tabuleiroPalpitesComputador[linha - 1][coluna - 1] != null) {
        linha = (1..numLinhas).random()
        coluna = (1..numColunas).random()
    }

    return Pair(linha,coluna)
}


fun contarNaviosDeDimensao(tabuleiroPalpites: Array<Array<Char?>>, dimensao: Int?): Int {

    numLinhas = tabuleiroPalpites.size
    numColunas = tabuleiroPalpites[0].size

    var naviosCompletos = 0
    var contador = 0

    when (dimensao) {
        1 -> for (linha in 1..numLinhas) {
            for (coluna in 1..numColunas) {
                if (tabuleiroPalpites[linha - 1][coluna - 1] != null &&
                    tabuleiroPalpites[linha - 1][coluna - 1] == '1') {
                    naviosCompletos++
                }
            }
        }
        2 -> for (linha in 1..numLinhas) {
            for (coluna in 1..numColunas) {
                if (tabuleiroPalpites[linha - 1][coluna - 1] != null &&
                    tabuleiroPalpites[linha - 1][coluna - 1] == '2') {
                    contador++
                    if (contador % dimensao == 0) {
                        naviosCompletos++
                    }
                }
            }
        }
        3 -> for (linha in 1..numLinhas) {
            for (coluna in 1..numColunas) {
                if (tabuleiroPalpites[linha - 1][coluna - 1] != null &&
                    tabuleiroPalpites[linha - 1][coluna - 1] == '3') {
                    contador++
                    if (contador % dimensao == 0) {
                        naviosCompletos++
                    }
                }
            }
        }
        4 -> for (linha in 1..numLinhas) {
            for (coluna in 1..numColunas) {
                if (tabuleiroPalpites[linha - 1][coluna - 1] != null &&
                    tabuleiroPalpites[linha - 1][coluna - 1] == '4') {
                    contador++
                    if (contador % dimensao == 0) {
                        naviosCompletos++
                    }
                }
            }
        }
    }
    return naviosCompletos
}


fun venceu(tabuleiroPalpites: Array<Array<Char?>>): Boolean {

    numLinhas = tabuleiroPalpites.size
    numColunas = tabuleiroPalpites[0].size

    val configuracaoJogo = calculaNumNavios(numLinhas, numColunas)

    if (configuracaoJogo.isEmpty()) {
        return false
    }

    var numTotalNavios = 0
    for (dimensao in 1..configuracaoJogo.size) {
        numTotalNavios += configuracaoJogo[dimensao - 1]
    }

    var numNaviosDimensao = 0
    for (dimensao in 1..configuracaoJogo.size) {
        if (configuracaoJogo[dimensao - 1] == contarNaviosDeDimensao(tabuleiroPalpites, dimensao)) {
            numNaviosDimensao += configuracaoJogo[dimensao - 1]
        }
    }
    return numNaviosDimensao == numTotalNavios
}


fun lerJogo(nomeFicheiro: String?, tipoTabuleiro: Int): Array<Array<Char?>> {


    return arrayOf(arrayOfNulls(0))
}


fun gravarJogo(nomeFicheiro: String?, tabuleiroRealHumano: Array<Array<Char?>>,
               tabuleiroPalpitesHumano: Array<Array<Char?>>,
               tabuleiroRealComputador: Array<Array<Char?>>,
               tabuleiroPalpitesComputador: Array<Array<Char?>>) {

    return
}


fun menuCriarTabuleiro() {

    var linhas: Int?
    var colunas: Int?
    do {
        println("\n> > Batalha Naval < <\n")
        println("Defina o tamanho do tabuleiro:")

        do {
            println("Quantas linhas?")
            linhas = readln().toIntOrNull()
            if (linhas == -1) return (main())
            if (linhas == null) println("!!! Número de linhas invalidas, tente novamente")
        } while (linhas == null)

        do {
            println("Quantas colunas?")
            colunas = readln().toIntOrNull()
            if (colunas == -1) return (main())
            if (colunas == null || colunas !in 1..26) println("!!! Número de colunas invalidas, tente novamente")


        } while (colunas == null || colunas !in 1..26)

        if (!tamanhoTabuleiroValido(linhas,colunas)) println("Tabuleiro invalido") else {
            tabuleiroHumano = criaTabuleiroVazio(linhas,colunas)
            for (posicao in 0 until obtemMapa(tabuleiroHumano,true).size) println(obtemMapa(tabuleiroHumano, true)[posicao])
        }
    } while (!tamanhoTabuleiroValido(linhas,colunas))

    numLinhas = linhas!!
    numColunas = colunas!!

    if (tabuleiroHumano[0].size == 4 && tabuleiroHumano.size == 4) {
        var coordenadasPrimeiroSubmarino: String
        var coordenadasSegundoSubmarino: String

        println("Insira as coordenadas de um submarino:")
        val perguntaCoordenadas = "Coordenadas? (ex: 6,G)"

        do {
            println(perguntaCoordenadas)
            coordenadasPrimeiroSubmarino = readln()
            if (coordenadasPrimeiroSubmarino == "-1") return (main())
            if (processaCoordenadas(coordenadasPrimeiroSubmarino, linhas, colunas) == null) println("!!! Coordenadas invalidas, tente novamente")
        } while (processaCoordenadas(coordenadasPrimeiroSubmarino, linhas, colunas) == null)

        val linhaPrimeiroSubmarino = processaCoordenadas(coordenadasPrimeiroSubmarino, linhas, colunas)!!.first
        val colunaPrimeiroSubmarino = processaCoordenadas(coordenadasPrimeiroSubmarino, linhas, colunas)!!.second
        insereNavioSimples(tabuleiroHumano,linhaPrimeiroSubmarino,colunaPrimeiroSubmarino, 1)

        for (posicao in 0 until obtemMapa(tabuleiroHumano, true).size) println(obtemMapa(tabuleiroHumano, true)[posicao])

        println("Insira as coordenadas de um submarino:")

        do {
            println(perguntaCoordenadas)
            coordenadasSegundoSubmarino = readln()
            val linhaSegundoSubmarino = processaCoordenadas(coordenadasSegundoSubmarino, linhas, colunas)!!.first
            val colunaSegundoSubmarino = processaCoordenadas(coordenadasSegundoSubmarino, linhas, colunas)!!.second
            if (coordenadasSegundoSubmarino == "-1") return (main())
            if (processaCoordenadas(coordenadasSegundoSubmarino, linhas, colunas) == null ||
                !estaLivre(tabuleiroHumano,gerarCoordenadasFronteira(tabuleiroHumano,linhaSegundoSubmarino,colunaSegundoSubmarino,"N",1))) {
                println("!!! Coordenadas invalidas, tente novamente")
            }
        } while (processaCoordenadas(coordenadasSegundoSubmarino, linhas, colunas) == null ||
            !estaLivre(tabuleiroHumano,gerarCoordenadasFronteira(tabuleiroHumano,linhaSegundoSubmarino, colunaSegundoSubmarino,"N",1)))

        val linhaSegundoSubmarino = processaCoordenadas(coordenadasSegundoSubmarino, linhas, colunas)!!.first
        val colunaSegundoSubmarino = processaCoordenadas(coordenadasSegundoSubmarino, linhas, colunas)!!.second
        insereNavioSimples(tabuleiroHumano,linhaSegundoSubmarino,colunaSegundoSubmarino,1)

        for (posicao in 0 until obtemMapa(tabuleiroHumano,true).size) println(obtemMapa(tabuleiroHumano,true)[posicao])

        tabuleiroComputador = criaTabuleiroVazio(numLinhas,numColunas)
        preencheTabuleiroComputador(tabuleiroComputador, calculaNumNavios(4, 4))

        println("Pretende ver o mapa gerado para o Computador? (S/N)")
        val verTabuleiroComputador = readLine()
        if (verTabuleiroComputador != null) {
            if (verTabuleiroComputador == "S") {
                for (posicao in 0 until obtemMapa(tabuleiroComputador, true).size) println(obtemMapa(tabuleiroComputador,true)[posicao])
            } else if (verTabuleiroComputador == "N") print("")
        }
        verificacao = true
    }
}


fun atiraEVence() {

    do {
        for (posicao in 0 until obtemMapa(tabuleiroPalpitesDoComputador,false).size) {
            println(obtemMapa(tabuleiroPalpitesDoComputador,false)[posicao])
        }
        println("Indique a posição que pretende atingir")
        do {
            println("Coordenadas? (ex: 6,G)")
            val coordenadasAtingir = readLine()
            if (coordenadasAtingir == null ||
                processaCoordenadas(coordenadasAtingir,numLinhas,numColunas) == null) println("\"!!! Coordenadas invalidas, tente novamente\"") else {
                val linhaAtingir = processaCoordenadas(coordenadasAtingir,numLinhas, numColunas)!!.first
                val colunaAtingir = processaCoordenadas(coordenadasAtingir,numLinhas, numColunas)!!.second

                lancarTiro(tabuleiroComputador,tabuleiroPalpitesDoComputador,Pair(linhaAtingir,colunaAtingir))
                println(">>> HUMANO >>>" + lancarTiro(tabuleiroComputador, tabuleiroPalpitesDoComputador, Pair(linhaAtingir,colunaAtingir)) +
                        if (navioCompleto(tabuleiroPalpitesDoComputador,linhaAtingir,colunaAtingir)) " Navio ao fundo!" else "")

                if (venceu(tabuleiroPalpitesDoComputador)) {
                    println("PARABENS! Venceu o jogo!")
                } else {
                    val coordenadasComputador = geraTiroComputador(tabuleiroPalpitesDoComputador)
                    val linhaComputador = coordenadasComputador.first
                    val colunaComputador = coordenadasComputador.second

                    println("Computador lancou tiro para a posicao ($linhaComputador,$colunaComputador)")
                    lancarTiro(tabuleiroHumano, tabuleiroPalpitesDoHumano, Pair(linhaComputador, colunaComputador))
                    println(">>> COMPUTADOR >>>" + lancarTiro(tabuleiroHumano, tabuleiroPalpitesDoHumano, Pair(linhaComputador, colunaComputador)) +
                            if (navioCompleto(tabuleiroPalpitesDoHumano,linhaComputador,colunaComputador)) " Navio ao fundo!" else "")

                    if (venceu(tabuleiroPalpitesDoHumano)) println("OPS! O computador venceu o jogo!") else {
                        do {
                            println("Prima enter para continuar")
                            val enter = readLine()
                        } while (enter == null)
                    }
                }
            }
        } while (coordenadasAtingir == null || processaCoordenadas(coordenadasAtingir,numLinhas,numColunas) == null)
    } while (!venceu(tabuleiroPalpitesDoComputador) && !venceu(tabuleiroPalpitesDoHumano))
}


fun main() {

    var escolha: Int?
    do {
        println("\n> > Batalha Naval < <\n")
        println("1 - Definir Tabuleiro e Navios")
        println("2 - Jogar")
        println("3 - Gravar")
        println("4 - Ler")
        println("0 - Sair\n")

        do {
            escolha = readln().toIntOrNull()
            if (escolha == -1) return(main()) else if (escolha == null || escolha !in 0..4) println("!!! Opcao invalida, tente novamente")
        } while (escolha == null || escolha !in -1..4)

        when (escolha) {
            1 -> menuCriarTabuleiro()
            2 -> if (!verificacao) println("!!! Tem que primeiro definir o tabuleiro do jogo, tente novamente") else {

                if (tabuleiroHumano[0].size == 4 && tabuleiroHumano.size == 4) {

                    tabuleiroPalpitesDoComputador = criaTabuleiroVazio(numLinhas,numColunas)
                    tabuleiroPalpitesDoHumano = criaTabuleiroVazio(numLinhas,numColunas)

                    atiraEVence()

                    do {
                        println("Prima enter para voltar ao menu principal")
                        val enter = readLine()
                    } while (enter == null)
                }
            }
            3 -> println("!!! POR IMPLEMENTAR, tente novamente")
            4 -> println("!!! POR IMPLEMENTAR, tente novamente")
        }
    } while (escolha != 0)
}