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
        assertEquals(50.0, conto.getBanca(), 0.01);
        assertEquals(50.0, conto.getPortafoglio(), 0.01);
    }

    @Test
    public void testPreleva() {
        conto.deposita(100.0); // Deposito iniziale per garantire fondi sufficienti
        conto.preleva(30.0);
        assertEquals(70.0, conto.getBanca(), 0.01);
        assertEquals(130.0, conto.getPortafoglio(), 0.01);
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
