import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContoBancarioTest {

    @Test
    public void testDeposita() {
        ContoBancario conto = new ContoBancario(1000);
        conto.deposita(500);
        assertEquals(1500, conto.getSaldo(), "Il saldo dovrebbe essere 1500 dopo il deposito.");
    }

    @Test
    public void testPreleva() {
        ContoBancario conto = new ContoBancario(1000);
        conto.preleva(200);
        assertEquals(800, conto.getSaldo(), "Il saldo dovrebbe essere 800 dopo il prelievo.");
    }

    @Test
    public void testInvestimento() {
        ContoBancario conto = new ContoBancario(1000);
        conto.investi(200);
        assertEquals(1200, conto.getSaldo(), "Il saldo dovrebbe essere 1200 dopo l'investimento.");
    }

    @Test
    public void testPrelievoFallito() {
        ContoBancario conto = new ContoBancario(1000);
        assertThrows(IllegalArgumentException.class, () -> conto.preleva(2000),
                "Il prelievo superiore al saldo dovrebbe lanciare un'eccezione.");
    }
}
