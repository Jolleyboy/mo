/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tests;

import java.util.logging.Level;
import java.util.logging.Logger;
import mo.MusicFile;
import mo.MusicIdentifier;
import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author Joshua
 */
public class musicIdentifierTest {
    MusicIdentifier mi = new MusicIdentifier();
    MusicFile mf;
    public musicIdentifierTest() {
        mf = new MusicFile("music\\seattle.mp3");
    }
    
    @Test
    public void fingerprinterTest() {
        String fingerprint = mi.fingerprint(mf);
        System.out.println(fingerprint);
        Assert.assertEquals(mi.fingerprint(mf), "167 AQADtIqySFokLXjy43iuIzcUakc45cb5oFG5odeO60GfBQ5t5K1Q-Sm0JScRksc_5MnwHdqLZhmDF5fRiD1KDnqUGqVnRMdzWBM-feBVwdL0AHmKayWaHs-Lf0TdoxFz4VFGgT90Hv0FK4mOHvcP5iGaHk-rQE-2o4luaA8-Pfhx0jiLhsuhfcd15D3awY2C44eP8BGa9SPaJA9x6ZCvwT-e5ZD2xMHf4tLRH15QcN7RHB11qMqH-oLHo7iyCF89MD6aB6eIC1UPkdbxw9HE4EgF_ygzNsIl9LngUjHCZ8Ql9Im0CKKp49uRD-b4oNSRL-GChxAv48clRbHgTDmuZtB7-Me7w_dR8tDyM8jHVHjQHE9yHF6FQNMvlN2FMEzCFBfOF36WELHO4TpU6sh39F9wTnD0oqeId6nQVFmOkJSLa0G_w6F6aCd0lbiO3vCzkMZ_4Y0Gxycu5DAjor6Nqzh7gYpePApyDf3RMFNC6KeO-0jzPPhm4VLy4wd8pnh-JCWRZwuuRMHRw72DPg-e68iToxlD3Ama5LiPNoIb_Ct-6A96Iz0eXDLSPcdjJccFXckVlAs7mJGOEj9inrgC_3jw6cg-H5V5NI_wHfrRUPnhSMrxTqgSBtKvItfQK8cPDxePLz58eQj1QkedIEzP49lRyeLQnA-u40qSH7_wwifyQEecFV-SxDh6-Ojz4Lrx6BIRUsqCyonw4zK-ozHyI0ff4A-09OiFS_AePN5x8vglhHEflGkJnEcoF5YVaGMuIjeD7zgVMEfz4Dly_WgW4ZIOfxPIotKPo86QTtvxBhX_4mKMZjqqHz9ySYebRDH0MUOuC02eo7eCS0mO2zia8-iDpEe-EF-k4jnQH03F4ImP6yLCLg8uD6ESRvhxlEoIF-iDvIH2FE0XfDnCJGtQ_ceP6wqaKpuGK7pQakJ15CZOF-JzhCofPNmDT8GN5jyY43kQcnmOZ8efomQi4j9OCb2Qvnh3gdaO60X6oGdw4Qq8pOGgh0UuXWgiZmjXDk-SH5eOo3mPPkRyIc6LK0kEVop3HIXfEbVyIf_x5DIuFq-Cy0V-1AmobjhyMegfaLlR40mMiiWapBnOD886hLwCMzvKsciFH9oZhOnk4LgyPcGL8_DjIheeg-FeqMtznIGay-iPo02GtMbRdgZ_oZLCHGGOR8HFHG546FHI4_IRCg3XDo_y41KOo0eYL9ARH1ySJMI1Dz_6wT9CK06h5yj14cpaSFlxOcjxJMdkeEeOXkyg5cZkPMlxOMGv40cpJVeQUpGG68J3xCc-Q3yE7gry9CHOgKPQHLXwHPmeGGWi4Df0KMPTHRdxJYfw4-KQUoenxkNxSgqew1Om41GUIL8g5ZKLMNmYB9_xJPlx498FP8MtJF4RazOeKPjRwz16PcYvhMzhiCEaNbgc7EebBC5-C4d2Fc2NOg8-6DKcYLwOnSi1WYGpI-dRFzF3_IEmH2H8xXj0IGfAf9DEo_mDM8dz9IP4ICaVBz1JfDKeHMzx4IcvcQh_Cc8DlT_0KMlzhJnEYz_8o9uInrBzlFkQ-oIYHSWVDOcjhHnQJ2gy9KlRroflx4juBuqRC32ESz-eiAfbrMjy5RB__DPy41syVM4efMeTI590_NCM9BxRKyyOeQk6TTdivUVzHfqOC_lQ60eTB9GPZwnw6dByCdfR_ETeXLgyHqyO_uCPE-mDPg1UX8FzXFl8oV9-6Dxy2MmLnsZ1jFtQSoc9sQg_Fvoi47uCRkZIskMZNT22i3CqPTiDc8h51FMW6KuCfDueoeRx_HiMMK0K9Y9C_HjwZD4eyD-a5witvniNXBK-RSe0H2H4Jbh04cFLhOehJZ-kI2uWXMaPKwt-XCYcF28eIdafot-RJyku7fBxlGWPsIlHNJNLHHKP8haasMaXC2dwGk0WHu0vdPsLz4d2HfUPD88UC-VDvDiLS0chnkavHPpSvDp-NCcXfAdfXCfOw8cPLSsr4cJzfBl8XNxBHw-TEprPwVMUfPCORx5cPdBznBnMHfrhHnaC6Uf4wO6DaZFyTPJypFyPH54UHJuDV8fzEWamB-WJ68KZGs09otQHPQs-H41zCW10_HnQd2ieQcdV5CqP6nAFBn0DV7PQ58iO68F9aPnBG-dxBjqhH86P70cznQqeHCiY_LiN__gWiYQaXkR7-Bl-Iy-u4z8ckUd_9PBzIXyDI6Zy9Ed6L_gDSg8qKWEGrVFmeOihizhDHWEUH-2JsodVXaiYsAipJhB_HfmkB30q40lwiUfhH30W5ISaI4_mCxePo3mN_uA_1Ee6nAzuRBGY6Md_XKhziEPxH_2DlByu_Mgl-MkgRTnuLDgfBdfRLEqGWjuOG10Zwu2RP-iSXsaJN5nRPOiR5yil4zf-hZAdHRW1C_pzXBJauD-q7gjdwtKO_ui1IYyWBNyiJYZepL-KL8uY4jiTHI-PZi8q58ih_kS-4lokEmyiHEfzo1aW4TnRIz8uJkqFTosC9ceFPEV53PCO_vhvhA50JegXIU4u3CGuZwif5PCkD2V-6Dq-HVUNfTEuNBLeJC8uaD-ah0fmBxdqBY2L5MyRi2FwsJGCHi-u4iQAogBjQCADiVGEQYEUcAwh4SRBRHgLiACEKMyAEQIYAJAACCkgjMAKCEAEUQoQKQgkCiACCCYAAAAMoYQIwhQQUgFmEGEASCcYQsYQIAQRQgAPFGDUCmAJYoIIgYQCjDBoLDICECaAEJAwAoghSgBBABOGGAWAAEIQBAwAyguggCAEGoAAAtQBBgQAzAKBlCDCAkEEEVYooxxCAgEHiCASAAcMOQYQgBACVAHDkDAAEaEEEMBIYBgDgjiAAFUWKAaYVcIgRogQwBghKKRAAQSAAtQBoYBxRgBmmTIKAEQAYIAAYIwwjggiFDRAAYMsUEIAwIgCShGFABQGY0UEEIBCJgyQAABCBCJAAcEQAkYAIwQAABNgCDASFKSYMQAoC5BgAggODGKAGCAAYYQShBRTzBkNgCCBEAaQYYgwwAACxgVFoAcGCMOIMQAgKgYjhFmpgFEWAGeMEA0hAAFgJAiCGEACQEGIYkgoYBQAhCDKCIPMQKOEAYgQBChAAgDCDAYAIYWBEkSYhhBCQIIhEBHEEMIEEQAgoggUAAhCNKYOAmEYQgYAAKgCRkgnkJBAASOYE0gIAhQxwAEnkFBGEAEgANQAIIDihggDgFBGIEUEEMBJAYxBCBkhgRFAEECFIEgJgoCwwCIlmAHEamccEEwJoiQAwBAlADICAYUEYUAAIpAUSAhACCAAWCGcIQYwhgAAQAIAlJHKEQSIU8JABIAARiCojEOKIQiAFMAQxggjCDggDAJKEAdEIIIQgoxACAkjGCBAAGKMMEAY04gASnBEgHJOKCCYQEoQggAAwAmiBDNGCEEJBgBQIIwB1GEClAEICACIUIgAYIQRQgDjABBEOgAEAEIgTYRggChgDASEICUcEEoJAhBVRjEGgHBGIAQcMQYYSIShgBhAgBIGACKSU4obA4RAhljJhAGCKACQAgYAgARSSASCBAIAMgQYMQsx");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ex) {
            Logger.getLogger(musicIdentifierTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
    public void musicIDTest() {
        mf = new MusicFile("music\\seattle.mp3");
        mi.parseJSON(mi.identify(mi.fingerprint(mf)),mf);
        Assert.assertEquals(mf.getArtist(), "Owl City");
        Assert.assertEquals(mf.getTitle(), "Hello Seattle");
        Assert.assertEquals(mf.getDuration(), "2:47");
}
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
