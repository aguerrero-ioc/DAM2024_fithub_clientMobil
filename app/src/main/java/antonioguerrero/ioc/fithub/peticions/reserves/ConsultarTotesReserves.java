package antonioguerrero.ioc.fithub.peticions.reserves;

import antonioguerrero.ioc.fithub.peticions.BasePeticions;

public class ConsultarTotesReserves extends BasePeticions {

    public ConsultarTotesReserves(BasePeticions.respostaServidorListener listener) {
        super(listener);
    }

    @Override
    public Class<?> obtenirTipusObjecte() {
        return Object[].class;
    }

    @Override
    public void execute() {
        // PENDENT
    }

    @Override
    public void respostaServidor(Object response) {
        // PENDENT
    }
}
