package com.maua.jogo;

import com.maua.jogo.model.DesafioTest;
import com.maua.jogo.model.FaseTest;
import com.maua.jogo.model.JogadorTest;
import com.maua.jogo.model.PartidaTest;
import com.maua.jogo.model.RespostaTest;
import com.maua.jogo.model.TabuleiroTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/** Suíte que agrupa todos os testes unitários de domínio (não exigem MySQL). */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    FaseTest.class,
    TabuleiroTest.class,
    JogadorTest.class,
    DesafioTest.class,
    PartidaTest.class,
    RespostaTest.class
})
public class AllTests {
}
