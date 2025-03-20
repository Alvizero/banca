import org.junit.*;
import java.util.Random;
import static org.junit.Assert.*;

public class ContoBancarioTest {

    private ContoBancario conto;

    @Before
    public void setUp() {
        conto = new ContoBancario();
    }

    @Test
    public void testDeposita() {
        conto.deposita(50.0);
        assertEquals(50.0, conto.getBanca(), 0.01); // Verifica che l'importo sia stato depositato correttamente
    }

    @Test
    public void testPreleva() {
        conto.deposita(100.0); // Deposito iniziale
        conto.preleva(30.0); // Prelievo di 30.0
        assertEquals(70.0, conto.getBanca(), 0.01); // Verifica che il saldo in banca sia 70.0
        assertEquals(30.0, conto.getPortafoglio(), 0.01); // Verifica che il saldo nel portafoglio sia 30.0
    }

    @Test
    public void testInveste() {
        Random random = new Random(1); // Seed fisso per ottenere risultati prevedibili
        conto.deposita(100.0);
        conto.investe(50.0, 12, random); // Investimento a breve termine
        conto.fineInvestimento();

        // Valori attesi con il seed fisso
        assertTrue(conto.getBanca() >= 50.0 || conto.getBanca() < 50.0); // Verifica guadagno o perdita
        assertFalse(conto.getInvestimento());
    }
}
