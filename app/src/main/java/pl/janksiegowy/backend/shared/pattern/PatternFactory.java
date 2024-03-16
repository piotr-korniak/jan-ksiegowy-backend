package pl.janksiegowy.backend.shared.pattern;

import pl.gov.crd.wzor._2021._12._27._11149.Ewidencja_JPK_V7K_2_v1_0e;
import pl.janksiegowy.backend.statement.JpkVat;

public class PatternFactory implements PatternId.PatternJpkVisitor<JpkVat>{

    @Override public JpkVat visit_JPK_V7K_2_v1_0e() {
        return Ewidencja_JPK_V7K_2_v1_0e.create();
    }

}
