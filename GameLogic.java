public class GameLogic {
    private int[] coins; // Arreglo de botes con monedas
    private boolean[] selected; // Arreglo para marcar los botes seleccionados
    private int currentPlayer = 1; // 1 para Jugador 1, 2 para Jugador 2
    private int scorePlayer1 = 0; // Acumulador de monedas para Jugador 1
    private int scorePlayer2 = 0; // Acumulador de monedas para Jugador 2

    // Constructor
    public GameLogic(int[] coins) {
        this.coins = coins;
        this.selected = new boolean[coins.length]; // Inicializa todos los botes como no seleccionados
    }

    // Método para obtener el arreglo de monedas
    public int[] getCoins() {
        return coins;
    }

    // Método para verificar si un bote ya ha sido seleccionado
    public boolean isBoteSelected(int index) {
        if (index < 0 || index >= coins.length) {
            throw new IllegalArgumentException("Índice de bote inválido");
        }
        return selected[index];
    }

    // Método para que el jugador elija un bote de los extremos disponibles
    public int chooseBote(int index) {
        // Verifica que el índice esté dentro de los extremos válidos
        if (index < 0 || index >= coins.length) {
            throw new IllegalArgumentException("Índice de bote inválido");
        }

        // Verifica si el bote está seleccionado o no
        if (selected[index]) {
            throw new IllegalStateException("Este bote ya ha sido seleccionado");
        }

        // Encuentra los índices de los extremos disponibles
        int leftIndex = 0;
        int rightIndex = coins.length - 1;

        // Encuentra los botes de los extremos disponibles
        while (leftIndex < rightIndex && (selected[leftIndex] || selected[rightIndex])) {
            if (selected[leftIndex]) {
                leftIndex++;
            }
            if (selected[rightIndex]) {
                rightIndex--;
            }
        }

        // Si no hay botes disponibles, lanzar excepción
        if (leftIndex > rightIndex) {
            throw new IllegalStateException("No quedan botes disponibles para seleccionar");
        }

        // Si el índice no corresponde a un extremo disponible, lanzar excepción
        if (index != leftIndex && index != rightIndex) {
            throw new IllegalArgumentException("Solo puedes elegir un bote de los extremos disponibles");
        }

        // Marca el bote como seleccionado
        selected[index] = true;

        // Sumar las monedas al jugador actual
        if (currentPlayer == 1) {
            scorePlayer1 += coins[index];
        } else {
            scorePlayer2 += coins[index];
        }

        // Devuelve las monedas del bote seleccionado
        return coins[index];
    }

    // Método para alternar el turno entre los jugadores
    public void switchPlayer() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }

    // Obtener el jugador actual
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    // Verifica si el juego ha terminado (todos los botes han sido seleccionados)
    public boolean isGameFinished() {
        for (boolean isSelected : selected) {
            if (!isSelected) {
                return false; // Si hay un bote no seleccionado, el juego no ha terminado
            }
        }
        return true; // Si todos los botes fueron seleccionados
    }

    // Método para obtener el puntaje del jugador
    public int getScore(int player) {
        if (player == 1) {
            return scorePlayer1;
        } else if (player == 2) {
            return scorePlayer2;
        } else {
            throw new IllegalArgumentException("Jugador inválido");
        }
    }

    // Método para determinar el ganador
    public String getWinner() {
        if (scorePlayer1 > scorePlayer2) {
            return "Jugador 1 gana con " + scorePlayer1 + " monedas.";
        } else if (scorePlayer2 > scorePlayer1) {
            return "Jugador 2 gana con " + scorePlayer2 + " monedas.";
        } else {
            return "Empate con " + scorePlayer1 + " monedas cada uno.";
        }
    }
}
