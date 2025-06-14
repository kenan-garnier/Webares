package com.webares.webares;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.IntConsumer;


public class Controller implements Initializable {
    private int pctrodes = 100, pctcl0 = 0, pctcl1 = 0, pctcl2 = 0, pctcl3 = 0, pctcl00, pctcl11 = 0, pctcl22 = 0, pctcl33 = 0, pctstl1 = 0, pctstl2 = 0, pctstl3 = 0, pctstl11 = 0, pctstl22 = 0, pctstl33 = 0;
    @FXML
    private ImageView r, cr, cm, cs1, cs2, cs3, pc1, pc2, pc3, clc1, clc2, clc3, pt1, pt2, pt3, clt0, clt1, clt2, clt3, sg1, sg2, sg3, vmscv1, vmscv2, vmscv3, mscv1, mscv2, mscv3, vbp1, vbp2, vbp3, bp1, bp2, bp3, tv1, tv2, tv3, te1, te2, te3;
    @FXML
    private Label time, i, tmp0, tmp1, tmp2, tmp3, psi0, psi1, psi2, psi3, kw1, kw2, kw3, a1, a2, a3;
    @FXML
    private Rectangle bg;
    @FXML
    public ImageView tc1, tc2, tc3;
    @FXML
    public Rectangle cl0, cl1, cl2, cl3,cl00, cl11, cl22, cl33, stl1, stl2, stl3, stl11, stl22, stl33;
    public ToggleButton automate;
    public TextArea logsautomate;
    private Timeline jaune, rouge, b1, b2, b3;
    private PauseTransition p2, p3;
    private AudioClip shutdown, nominal, maximal, start, stop, fullstart, circulate1, circulate2, reactive1, reactive2, integrity1, integrity2, integrity3, yellow, red, meltdown, boom;
    private boolean bcirculate = false, bnominal = false, bmaximal = false, bturbine = false, breactive = false, bintegrity1 = false, bintegrity2 = false, bintegrity3 = false, bboom = false;
    public boolean bautomate = false;
    private Controller2 controller2;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logsautomate.setStyle(
                "-fx-background-color: black;" +
                "-fx-control-inner-background: black;" +
                "-fx-text-fill: white;"
        );
        logsautomate.setText("Automate" + " \n" + "DISABLED");
        automate.setStyle("-fx-text-fill: red;");
        automate.setOnAction(event -> {
            bautomate = automate.isSelected();
            if (bautomate) {
                automate.setStyle("-fx-text-fill: green;");
                logsautomate.setText("Automate" + " \n" + "ENABLED");
            }else{
                automate.setStyle("-fx-text-fill: red;");
                logsautomate.setText("Automate" + " \n" + "DISABLED");
            }
        });
    }
    public void starter() {
        Platform.runLater(() -> {
            for (Node node : logsautomate.lookupAll(".scroll-bar")) {
                if (node instanceof ScrollBar) {
                    ScrollBar sb = (ScrollBar) node;
                    sb.setOpacity(0);
                    sb.setDisable(true);
                    sb.setManaged(false);
                }
            }
            Node corner = logsautomate.lookup(".corner");
            if (corner != null) {
                corner.setOpacity(0);
                corner.setDisable(true);
                corner.setManaged(false);
            }
        });
        WebAPI api = new WebAPI(this);
        controller2 = new Controller2(this,api);
        api.setController2(controller2);
        api.webgetter(true);
        jaune = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(bg.fillProperty(), Color.BLACK)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(bg.fillProperty(), Color.YELLOW)),
                new KeyFrame(Duration.seconds(2), new KeyValue(bg.fillProperty(), Color.BLACK))
        );
        jaune.setCycleCount(Timeline.INDEFINITE);
        rouge = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(bg.fillProperty(), Color.BLACK)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(bg.fillProperty(), Color.RED)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(bg.fillProperty(), Color.BLACK))
        );
        rouge.setCycleCount(Timeline.INDEFINITE);
        b1 = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(bg.fillProperty(), Color.RED)),
                new KeyFrame(Duration.seconds(14.48), new KeyValue(bg.fillProperty(), Color.MAGENTA))
        );
        b2 = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(bg.fillProperty(), Color.MAGENTA)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(bg.fillProperty(), Color.WHITE)),
                new KeyFrame(Duration.seconds(5), new KeyValue(bg.fillProperty(), Color.YELLOW))
        );
        b3 = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(bg.fillProperty(), Color.YELLOW)),
                new KeyFrame(Duration.seconds(3), new KeyValue(bg.fillProperty(), Color.BLUE))
        );
        p2 = new PauseTransition(Duration.seconds(14.48));
        p3 = new PauseTransition(Duration.seconds(27));

        String base = "/com/webares/webares/sons/";
        shutdown   = loadClip(base + "SHUTDOWN.wav");
        nominal    = loadClip(base + "NOMINAL.wav");
        maximal    = loadClip(base + "MAXIMAL.wav");
        start      = loadClip(base + "START T.wav");
        stop       = loadClip(base + "STOP T.wav");
        fullstart  = loadClip(base + "FULL START.wav");
        circulate1 = loadClip(base + "CIRCULATE.wav");
        circulate2 = loadClip(base + "CIRCULATE2.wav");
        reactive1  = loadClip(base + "REACTIVE.wav");
        reactive2  = loadClip(base + "REACTIVE2.wav");
        integrity1 = loadClip(base + "INTEGRITY1.wav");
        integrity2 = loadClip(base + "INTEGRITY2.wav");
        integrity3 = loadClip(base + "INTEGRITY3.wav");
        yellow     = loadClip(base + "JAUNE.wav");
        red        = loadClip(base + "ROUGE.wav");
        meltdown   = loadClip(base + "MELTDOWN.wav");
        boom       = loadClip(base + "BOOM.wav");

        shutdown.play();
    }

    private AudioClip loadClip(String path) {
        return new AudioClip(Objects.requireNonNull(getClass().getResource(path)).toString());
    }

    public void process() {
        Platform.runLater(this::compteur);
    }

    private void compteur() {
        DecimalFormat df = new DecimalFormat("#0.00");
        double i1_1 = Double.parseDouble(DATA_Web.table.get("CORE_INTEGRITY").replace(",", "."));
        double clc0_1 = Double.parseDouble(DATA_Web.table.get("COOLANT_CORE_QUANTITY_IN_VESSEL").replace(",", "."));
        double pc1_1 = Double.parseDouble(DATA_Web.table.get("COOLANT_CORE_CIRCULATION_PUMP_0_STATUS").replace(",", "."));
        double pc2_1 = Double.parseDouble(DATA_Web.table.get("COOLANT_CORE_CIRCULATION_PUMP_1_STATUS").replace(",", "."));
        double pc3_1 = Double.parseDouble(DATA_Web.table.get("COOLANT_CORE_CIRCULATION_PUMP_2_STATUS").replace(",", "."));
        double pc1_2 = Double.parseDouble(DATA_Web.table.get("COOLANT_CORE_CIRCULATION_PUMP_0_DRY_STATUS").replace(",", "."));
        double pc2_2 = Double.parseDouble(DATA_Web.table.get("COOLANT_CORE_CIRCULATION_PUMP_1_DRY_STATUS").replace(",", "."));
        double pc3_2 = Double.parseDouble(DATA_Web.table.get("COOLANT_CORE_CIRCULATION_PUMP_2_DRY_STATUS").replace(",", "."));
        double clc1_1 = Double.parseDouble(DATA_Web.table.get("COOLANT_CORE_CIRCULATION_PUMP_0_SPEED").replace(",", "."));
        double clc2_1 = Double.parseDouble(DATA_Web.table.get("COOLANT_CORE_CIRCULATION_PUMP_1_SPEED").replace(",", "."));
        double clc3_1 = Double.parseDouble(DATA_Web.table.get("COOLANT_CORE_CIRCULATION_PUMP_2_SPEED").replace(",", "."));
        double pt1_1 = Double.parseDouble(DATA_Web.table.get("COOLANT_SEC_CIRCULATION_PUMP_0_STATUS").replace(",", "."));
        double pt2_1 = Double.parseDouble(DATA_Web.table.get("COOLANT_SEC_CIRCULATION_PUMP_1_STATUS").replace(",", "."));
        double pt3_1 = Double.parseDouble(DATA_Web.table.get("COOLANT_SEC_CIRCULATION_PUMP_2_STATUS").replace(",", "."));
        double pt1_2 = Double.parseDouble(DATA_Web.table.get("COOLANT_SEC_CIRCULATION_PUMP_0_DRY_STATUS").replace(",", "."));
        double pt2_2 = Double.parseDouble(DATA_Web.table.get("COOLANT_SEC_CIRCULATION_PUMP_1_DRY_STATUS").replace(",", "."));
        double pt3_2 = Double.parseDouble(DATA_Web.table.get("COOLANT_SEC_CIRCULATION_PUMP_2_DRY_STATUS").replace(",", "."));
        double clt0_1 = Double.parseDouble(DATA_Web.table.get("CONDENSER_CIRCULATION_PUMP_SPEED").replace(",", "."));
        double clt0_2 = Double.parseDouble(DATA_Web.table.get("CONDENSER_VOLUME").replace(",", "."));
        double clt1_1 = Double.parseDouble(DATA_Web.table.get("COOLANT_SEC_CIRCULATION_PUMP_0_SPEED").replace(",", "."));
        double clt2_1 = Double.parseDouble(DATA_Web.table.get("COOLANT_SEC_CIRCULATION_PUMP_1_SPEED").replace(",", "."));
        double clt3_1 = Double.parseDouble(DATA_Web.table.get("COOLANT_SEC_CIRCULATION_PUMP_2_SPEED").replace(",", "."));
        double clt11_1 = Double.parseDouble(DATA_Web.table.get("COOLANT_SEC_0_VOLUME").replace(",", "."));
        double clt22_1 = Double.parseDouble(DATA_Web.table.get("COOLANT_SEC_1_VOLUME").replace(",", "."));
        double clt33_1 = Double.parseDouble(DATA_Web.table.get("COOLANT_SEC_2_VOLUME").replace(",", "."));
        double psi1_1 = Double.parseDouble(DATA_Web.table.get("COOLANT_SEC_0_PRESSURE").replace(",", "."));
        double psi2_1 = Double.parseDouble(DATA_Web.table.get("COOLANT_SEC_1_PRESSURE").replace(",", "."));
        double psi3_1 = Double.parseDouble(DATA_Web.table.get("COOLANT_SEC_2_PRESSURE").replace(",", "."));
        double vmscv1_1 = Double.parseDouble(DATA_Web.table.get("MSCV_0_OPENING_ACTUAL").replace(",", "."));
        double vmscv2_1 = Double.parseDouble(DATA_Web.table.get("MSCV_1_OPENING_ACTUAL").replace(",", "."));
        double vmscv3_1 = Double.parseDouble(DATA_Web.table.get("MSCV_2_OPENING_ACTUAL").replace(",", "."));
        double vbp1_1 = Double.parseDouble(DATA_Web.table.get("STEAM_TURBINE_0_BYPASS_ACTUAL").replace(",", "."));
        double vbp2_1 = Double.parseDouble(DATA_Web.table.get("STEAM_TURBINE_1_BYPASS_ACTUAL").replace(",", "."));
        double vbp3_1 = Double.parseDouble(DATA_Web.table.get("STEAM_TURBINE_2_BYPASS_ACTUAL").replace(",", "."));
        double tmp0_1 = Double.parseDouble(DATA_Web.table.get("CORE_TEMP").replace(",", "."));
        double tmp1_1 = Double.parseDouble(DATA_Web.table.get("STEAM_TURBINE_0_TEMPERATURE").replace(",", "."));
        double tmp2_1 = Double.parseDouble(DATA_Web.table.get("STEAM_TURBINE_1_TEMPERATURE").replace(",", "."));
        double tmp3_1 = Double.parseDouble(DATA_Web.table.get("STEAM_TURBINE_2_TEMPERATURE").replace(",", "."));
        double psi0_2 = Double.parseDouble(DATA_Web.table.get("CORE_PRESSURE").replace(",", "."));
        double psi1_2 = Double.parseDouble(DATA_Web.table.get("STEAM_TURBINE_0_PRESSURE").replace(",", "."));
        double psi2_2 = Double.parseDouble(DATA_Web.table.get("STEAM_TURBINE_1_PRESSURE").replace(",", "."));
        double psi3_2 = Double.parseDouble(DATA_Web.table.get("STEAM_TURBINE_2_PRESSURE").replace(",", "."));
        double rpm1_1 = Double.parseDouble(DATA_Web.table.get("STEAM_TURBINE_0_RPM").replace(",", "."));
        double rpm2_1 = Double.parseDouble(DATA_Web.table.get("STEAM_TURBINE_1_RPM").replace(",", "."));
        double rpm3_1 = Double.parseDouble(DATA_Web.table.get("STEAM_TURBINE_2_RPM").replace(",", "."));
        double v1_1 = Double.parseDouble(DATA_Web.table.get("GENERATOR_0_V").replace(",", "."));
        double v2_1 = Double.parseDouble(DATA_Web.table.get("GENERATOR_1_V").replace(",", "."));
        double v3_1 = Double.parseDouble(DATA_Web.table.get("GENERATOR_2_V").replace(",", "."));
        double a1_1 = Double.parseDouble(DATA_Web.table.get("GENERATOR_0_A").replace(",", "."));
        double a2_1 = Double.parseDouble(DATA_Web.table.get("GENERATOR_1_A").replace(",", "."));
        double a3_1 = Double.parseDouble(DATA_Web.table.get("GENERATOR_2_A").replace(",", "."));
        double kw1_1 = Double.parseDouble(DATA_Web.table.get("GENERATOR_0_KW").replace(",", "."));
        double kw2_1 = Double.parseDouble(DATA_Web.table.get("GENERATOR_1_KW").replace(",", "."));
        double kw3_1 = Double.parseDouble(DATA_Web.table.get("GENERATOR_2_KW").replace(",", "."));
        double hz1_1 = Double.parseDouble(DATA_Web.table.get("GENERATOR_0_HERTZ").replace(",", "."));
        double hz2_1 = Double.parseDouble(DATA_Web.table.get("GENERATOR_1_HERTZ").replace(",", "."));
        double hz3_1 = Double.parseDouble(DATA_Web.table.get("GENERATOR_2_HERTZ").replace(",", "."));
        boolean te1_1 = Boolean.parseBoolean(DATA_Web.table.get("GENERATOR_0_BREAKER").toLowerCase());
        boolean te2_1 = Boolean.parseBoolean(DATA_Web.table.get("GENERATOR_1_BREAKER").toLowerCase());
        boolean te3_1 = Boolean.parseBoolean(DATA_Web.table.get("GENERATOR_2_BREAKER").toLowerCase());

// Sound
        if (DATA_Web.table.get("CORE_STATE").equalsIgnoreCase("reactivo")) {
            if (!breactive && !bnominal) {
                reactive1.play();
                Timeline delay = new Timeline(new KeyFrame(Duration.seconds(5.19), e -> nominal.play()));
                delay.play();
                breactive = true;
                bnominal = true;
            }
        } else {
            if (breactive) {
                reactive2.play();
                breactive = false;
            }
            if (bnominal) {
                if (!bmaximal) {
                    if (reactive2.isPlaying()) {
                        Timeline delay = new Timeline(new KeyFrame(Duration.seconds(6.05), e -> shutdown.play()));
                        delay.play();
                    } else {
                        shutdown.play();
                    }
                    bnominal = false;
                }
            }
        }
        if (i1_1 < 75 && i1_1 >= 70.0) {
            if (!bintegrity1) {
                integrity1.play();
                bintegrity1 = true;
            }
            if (!yellow.isPlaying() && !integrity1.isPlaying() && !integrity2.isPlaying()) yellow.play();
        } else if (i1_1 < 70 && i1_1 >= 50.0) {
            if (!bintegrity2) {
                yellow.stop();
                integrity1.stop();
                integrity2.play();
                bintegrity2 = true;
            }
            if (!yellow.isPlaying() && !integrity2.isPlaying() && !integrity3.isPlaying()) yellow.play();
            if (!red.isPlaying() && !integrity2.isPlaying() && !integrity3.isPlaying()) red.play();
        } else if (i1_1 < 50 && i1_1 >= 1) {
            if (!bintegrity3) {
                yellow.stop();
                red.stop();
                integrity1.stop();
                integrity2.stop();
                integrity3.play();
                bintegrity3 = true;
            }
            if (!meltdown.isPlaying() && !integrity3.isPlaying()) meltdown.play();
        } else if (i1_1 < 1) {
            if (!bboom) {
                meltdown.stop();
                bg.setFill(Color.RED);
                boom.play();
                p2.play();
                p3.play();
                b1.play();
                p2.setOnFinished(e -> b2.play());
                p3.setOnFinished(e -> b3.play());

                bboom = true;
            }
        } else {
            integrity1.stop();
            integrity2.stop();
            integrity3.stop();
            meltdown.stop();
            yellow.stop();
            red.stop();
            bintegrity1 = false;
            bintegrity2 = false;
            bintegrity3 = false;
            bboom = false;
        }
        if (clc1_1 >= 1.0 || clc2_1 >= 1.0 || clc3_1 >= 1.0) {
            if (!bcirculate) {
                circulate1.play();
                bcirculate = true;
            }
        } else {
            if (bcirculate) {
                circulate2.play();
                bcirculate = false;
            }
        }
        if (
                ((int) Math.floor(v1_1) == 22001 && hz1_1 == 50 && rpm1_1 == 3060 && !te1_1) &&
                ((int) Math.floor(v2_1) == 22001 && hz2_1 == 50 && rpm2_1 == 3060 && !te2_1) &&
                ((int) Math.floor(v3_1) == 22001 && hz3_1 == 50 && rpm3_1 == 3060 && !te3_1)
        ) {
            if (!bmaximal) {
                fullstart.play();
                Timeline delay = new Timeline(new KeyFrame(Duration.seconds(3.75), e -> maximal.play()));
                delay.play();
                bmaximal = true;
            }
        } else {
            if (bmaximal) {
                nominal.play();
                bmaximal = false;
            }
        }
        if (kw1_1 >= 1.0 || kw2_1 >= 1.0 || kw3_1 >= 1.0) {
            if (!bturbine) {
                start.play();
                bturbine = true;
            }
        } else {
            if (bturbine) {
                stop.play();
                bturbine = false;
            }
        }

// Methode
        rodes(Double.parseDouble(DATA_Web.table.get("RODS_POS_ACTUAL").replace(",", ".")));
        cstat(
                DATA_Web.table.get("CORE_STATE"),
                Boolean.parseBoolean(DATA_Web.table.get("CORE_CRITICAL_MASS_REACHED").toLowerCase()),
                Boolean.parseBoolean(DATA_Web.table.get("CORE_STEAM_PRESENT").toLowerCase()),
                Boolean.parseBoolean(DATA_Web.table.get("CORE_HIGH_STEAM_PRESENT").toLowerCase()),
                i1_1
        );
        strmp(rpm1_1, rpm2_1, rpm3_1);

        gen0(rpm1_1, v1_1, hz1_1, tv1, mscv1);
        gen0(rpm2_1, v2_1, hz2_1, tv2, mscv2);
        gen0(rpm3_1, v3_1, hz3_1, tv3, mscv3);

        cl0level(clc0_1);

        cl0level(cl1, clc1_1, pctcl1, val -> pctcl1 = val);
        cl0level(cl2, clc2_1, pctcl2, val -> pctcl2 = val);
        cl0level(cl3, clc3_1, pctcl3, val -> pctcl3 = val);

        cl1level(clt0_2,pctcl00, val -> pctcl00 = val);

        cl1level(cl11, clt11_1, pctcl11, val -> pctcl11 = val );
        cl1level(cl22, clt22_1, pctcl22, val -> pctcl22 = val );
        cl1level(cl33, clt33_1, pctcl33, val -> pctcl33 = val );

        st1level(stl11, psi1_1, pctstl11, val -> pctstl11 = val);
        st1level(stl22, psi2_1, pctstl22, val -> pctstl22 = val);
        st1level(stl33, psi3_1, pctstl33, val -> pctstl33 = val);

        st0level(stl1, psi1_2, pctstl1, val -> pctstl1 = val);
        st0level(stl2, psi2_2, pctstl2, val -> pctstl2 = val);
        st0level(stl3, psi3_2, pctstl3, val -> pctstl3 = val);

// Compteur
        time.setText(DATA_Web.table.get("TIME"));
        i.setText(df.format(i1_1));
        tmp0.setText(df.format(tmp0_1));
        tmp1.setText(df.format(tmp1_1));
        tmp2.setText(df.format(tmp2_1));
        tmp3.setText(df.format(tmp3_1));
        psi0.setText(df.format(psi0_2));
        psi1.setText(df.format(psi1_2));
        psi2.setText(df.format(psi2_2));
        psi3.setText(df.format(psi3_2));
        kw1.setText(df.format(kw1_1));
        kw2.setText(df.format(kw2_1));
        kw3.setText(df.format(kw3_1));
        a1.setText(df.format(a1_1));
        a2.setText(df.format(a2_1));
        a3.setText(df.format(a3_1));

// Transformer
        te1.setVisible(!te1_1);
        te2.setVisible(!te2_1);
        te3.setVisible(!te3_1);

// Circuit + pompe

// Core
        pc1.setVisible(pc1_1 == 1 || pc1_1 == 2);
        clc1.setVisible(pc1_2 != 1 && clc1_1 >= 1.0);
        pc2.setVisible(pc2_1 == 1 || pc2_1 == 2);
        clc2.setVisible(pc2_2 != 1 && clc2_1 >= 1.0);
        pc3.setVisible(pc3_1 == 1 || pc3_1 == 2);
        clc3.setVisible(pc3_2 != 1 && clc3_1 >= 1.0);
// Steam Gen
        sg1.setVisible(psi1_1 >= 2.0);
        sg2.setVisible(psi2_1 >= 2.0);
        sg3.setVisible(psi3_1 >= 2.0);
        vmscv1.setVisible(vmscv1_1 >= 1.0);
        vmscv2.setVisible(vmscv2_1 >= 1.0);
        vmscv3.setVisible(vmscv3_1 >= 1.0);
        mscv1.setVisible(psi1_2 >= 2.0);
        mscv2.setVisible(psi2_2 >= 2.0);
        mscv3.setVisible(psi3_2 >= 2.0);
        vbp1.setVisible(vbp1_1 >= 1.0);
        vbp2.setVisible(vbp2_1 >= 1.0);
        vbp3.setVisible(vbp3_1 >= 1.0);
        bp1.setVisible(vbp1_1 >= 1.0 && psi1_1 >= 2.0);
        bp2.setVisible(vbp2_1 >= 1.0 && psi2_1 >= 2.0);
        bp3.setVisible(vbp3_1 >= 1.0 && psi3_1 >= 2.0);
// Turbine
        clt0.setVisible(clt0_1 >= 1.0);
        pt1.setVisible(pt1_1 == 1 || pt1_1 == 2);
        clt1.setVisible(pt1_2 != 1 && clt1_1 >= 1.0);
        pt2.setVisible(pt2_1 == 1 || pt2_1 == 2);
        clt2.setVisible(pt2_2 != 1 && clt2_1 >= 1.0);
        pt3.setVisible(pt3_1 == 1 || pt3_1 == 2);
        clt3.setVisible(pt3_2 != 1 && clt3_1 >= 1.0);
    }

    private void strmp(double rmp1, double rmp2, double rmp3) {
        tc1.setRotate(tc1.getRotate() + (rmp1 * 30) / 3060);
        tc2.setRotate(tc2.getRotate() + (rmp2 * 30) / 3060);
        tc3.setRotate(tc3.getRotate() + (rmp3 * 30) / 3060);
    }

    private void rodes(double dpourcent) {
        int pourcent = (int) dpourcent;
        if (pourcent != pctrodes) {
            double cote = (pourcent < pctrodes) ? -6.04 : 6.04;

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(100), e -> cr.setTranslateY(cr.getTranslateY() + cote))
            );
            timeline.setCycleCount(Math.abs(pctrodes - pourcent));
            timeline.play();
            pctrodes = pourcent;
        }
    }

    private void gen0(double rpm, double volts, double hz, ImageView tv, ImageView mscv) {
        if (rpm > 0) {
            if ((int) Math.floor(volts) != 22001 || hz != 50 || rpm != 3060) {
                tv.setVisible(!tv.isVisible());
            } else {
                tv.setVisible(false); // signal normal
            }
        } else tv.setVisible(mscv.isVisible());
    }

    private void cl0level(double quant) {
        quant = Math.ceil((quant * 100.0) / 120000.0);
        if ((int) quant != pctcl0) {
            double cote = (quant < pctcl0) ? -6.26 : 6.26;
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(10), e -> {
                        cl0.setHeight(cl0.getHeight() + cote);
                        cl0.setLayoutY(cl0.getLayoutY() - cote);
                    })
            );
            timeline.setCycleCount(Math.abs((int) (pctcl0 - quant)));
            timeline.play();
            pctcl0 = (int) quant;
        }
    }

    private void cl0level(Rectangle rect, double dpourcent, int currentPct, IntConsumer pctUpdater) {
        int newPct = (int) Math.ceil((dpourcent * 100.0) / 100);

        if (newPct != currentPct) {
            double step = (newPct < currentPct) ? -0.78 : 0.78;

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(10), e -> {
                        rect.setHeight(rect.getHeight() + step);
                        rect.setLayoutY(rect.getLayoutY() - step);
                    })
            );
            timeline.setCycleCount(Math.abs(newPct - currentPct));
            timeline.play();

            pctUpdater.accept(newPct);
        }
    }
    private void cl1level(double quant, int currentPct, IntConsumer pctUpdater) {
        quant = Math.ceil((quant * 100.0) / 360000.0);
        int newPct = (int) quant;

        if (newPct != currentPct) {
            double step = (newPct < currentPct) ? -9.48 : 9.48;

            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> {
                cl00.setHeight(cl00.getHeight() + step);
                cl00.setLayoutY(cl00.getLayoutY() - step);
            }));
            timeline.setCycleCount(Math.abs(newPct - currentPct));
            timeline.play();

            pctUpdater.accept(newPct);
        }
    }
    private void cl1level(Rectangle rect, double quant, int currentPct, IntConsumer pctUpdater) {
        quant = Math.ceil((quant * 100.0) / 60000.0);
        int newPct = (int) quant;

        if (newPct != currentPct) {
            double step = (newPct < currentPct) ? -1.26 : 1.26;

            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> {
                rect.setHeight(rect.getHeight() + step);
                rect.setLayoutY(rect.getLayoutY() - step);
            }));
            timeline.setCycleCount(Math.abs(newPct - currentPct));
            timeline.play();

            pctUpdater.accept(newPct);
        }
    }
    private void st0level(Rectangle rect, double quant, int currentPct, IntConsumer pctUpdater) {
        quant = Math.ceil((quant * 100.0) / 120.0);
        int newPct = (int) quant;

        if (newPct != currentPct) {
            double step = (newPct < currentPct) ? -0.48 : 0.48;

            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> {
                rect.setHeight(rect.getHeight() + step);
                rect.setLayoutY(rect.getLayoutY() - step);
            }));
            timeline.setCycleCount(Math.abs(newPct - currentPct));
            timeline.play();

            pctUpdater.accept(newPct);
        }
    }
    private void st1level(Rectangle rect, double quant, int currentPct, IntConsumer pctUpdater) {
        quant = Math.ceil((quant * 100.0) / 120.0);
        int newPct = (int) quant;

        if (newPct != currentPct) {
            double step = (newPct < currentPct) ? -1.30 : 1.30;

            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> {
                rect.setHeight(rect.getHeight() + step);
                rect.setLayoutY(rect.getLayoutY() - step);
            }));
            timeline.setCycleCount(Math.abs(newPct - currentPct));
            timeline.play();

            pctUpdater.accept(newPct);
        }
    }

    private void cstat(String status, boolean reached, boolean cs, boolean hcs, double integrity) {
// Combustible en Vert si réactif
        r.setVisible(status.equalsIgnoreCase("reactivo"));

// Clignotement logo radio en cas de radioactivité
        if (reached) {
            cm.setVisible(!cm.isVisible()); // Clignotement de cm
        } else {
            cm.setVisible(false);
        }

// Clignotement réacteur en cas de Steam
        if (integrity >= 1) {
            cs3.setVisible(false);
            if (hcs) {
                cs1.setVisible(true);  // cs1 reste toujours visible
                cs2.setVisible(!cs2.isVisible()); // cs2 clignote seul
            } else {
                cs2.setVisible(false);
                if (cs) {
                    cs1.setVisible(!cs1.isVisible()); // cs1 clignote
                } else {
                    cs1.setVisible(false); // cs1 disparaît
                }
            }
        } else {
            cs1.setVisible(false);
            cs2.setVisible(false);
            cs3.setVisible(true);

        }


// Clignotement interface en cas de problème d'Intégrité
        if (integrity < 75.0 && integrity >= 70.0) {
            jaune.play();
        } else if (integrity < 70 && integrity >= 1) {
            jaune.stop();
            rouge.play();
        } else if (integrity == 0) {
            rouge.stop();
        } else {
            jaune.stop();
            rouge.stop();
            bg.setFill(Color.BLACK);
        }
    }
}