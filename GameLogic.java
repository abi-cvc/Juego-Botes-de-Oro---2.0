public class GameLogic {
    private int[] coins; // Arreglo de botes con monedas
    private boolean[] selected; // Arreglo para marcar los botes seleccionados

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

    // Método para que el jugador elija un bote
    public int chooseBote(int index) {
        if (index < 0 || index >= coins.length) {
            throw new IllegalArgumentException("Índice de bote inválido");
        }
        if (selected[index]) {
            throw new IllegalStateException("Este bote ya ha sido seleccionado");
        }
        selected[index] = true; // Marca el bote como seleccionado
        return coins[index]; // Devuelve las monedas del bote seleccionado
    }
}
