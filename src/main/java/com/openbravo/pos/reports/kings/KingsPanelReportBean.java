package com.openbravo.pos.reports.kings;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.*;
import com.openbravo.data.user.EditorCreator;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.BeanFactoryException;
import com.openbravo.pos.reports.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class KingsPanelReportBean extends JPanelReport {

    private String title;
    private String report;

    private String resourcebundle = null;

    private String sentence;

    private List<Datas> fielddatas = new ArrayList<>();
    private List<String> fieldnames = new ArrayList<>();

    private List<String> paramnames = new ArrayList<>();

    private JParamsComposed qbffilter = new JParamsComposed();

    private Date startDate;
    private Date endDate;
    private String jenisTransaksi;

    @Override
    public void init(AppView app) throws BeanFactoryException {

        qbffilter.init(app);
        super.init(app);
    }

    @Override
    public void activate() throws BasicException {

        qbffilter.activate();
        super.activate();

        if (qbffilter.isEmpty()) {
            setVisibleFilter(false);
            setVisibleButtonFilter(false);
        }
    }

    @Override
    protected EditorCreator getEditorCreator() {

        return qbffilter;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getJenisTransaksi() {
        return jenisTransaksi;
    }

    public void setJenisTransaksi(String jenisTransaksi) {
        this.jenisTransaksi = jenisTransaksi;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    protected String getReport() {
        return report;
    }

    @Override
    protected String getResourceBundle() {
        return resourcebundle == null
                ? report
                : resourcebundle;
    }

    @Override
    protected BaseSentence getSentence() {
        return new StaticSentence(m_App.getSession()
                , new QBFBuilder(sentence, paramnames.toArray(new String[paramnames.size()]))
                , qbffilter.getSerializerWrite()
                , new SerializerReadBasic(fielddatas.toArray(new Datas[fielddatas.size()])));
    }

    @Override
    protected ReportFields getReportFields() {
        return new ReportFieldsArray(fieldnames.toArray(new String[fieldnames.size()]));
    }

    public void setTitleKey(String titlekey) {
        title = AppLocal.getIntString(titlekey);
    }

    public void setReport(String report) {
        this.report = report;
    }

    public void setResourceBundle(String resourcebundle) {
        this.resourcebundle = resourcebundle;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public void addParameter(String name) {
        paramnames.add(name);
    }

    public void addQBFFilter(ReportEditorCreator qbff) {
        qbffilter.addEditor(qbff);
    }

    public void addField(String name, Datas data) {
        fieldnames.add(name);
        fielddatas.add(data);
    }

    @Override
    protected void launchreport() {
        m_App.waitCursorBegin();

        if (jr != null) {
            try {
                String res = getResourceBundle();

                Object params = (editor == null) ? null : editor.createValue();
                Connection conn = m_App.getSession().getConnection();

                Map reportparams = new HashMap();

                // ada di params ngambil filternya
                Date tglTransaksi = new Date();
                String jenisTransaksi = null;


                if (params != null) {
                    tglTransaksi = (Date) Array.get(Array.get(params, 0), 1);
                    if (Array.getLength(params) > 1) {
                        jenisTransaksi = (String) Array.get(Array.get(params, 1), 1);

                        if (jenisTransaksi.equals("tunai")) {
                            jenisTransaksi = "cash";
                        } else {
                            jenisTransaksi = "debt";
                        }
                    }
                }

                reportparams.put("TGL_TRANSAKSI", tglTransaksi);
                reportparams.put("JENIS_TRANSAKSI", jenisTransaksi);

                JasperPrint jp = JasperFillManager.fillReport(jr, reportparams, conn);

                reportviewer.loadJasperPrint(jp);

                setVisibleFilter(false);

            } catch (MissingResourceException e) {
                MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotloadresourcedata"), e);
                msg.show(this);
            } catch (JRException e) {
                MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotfillreport"), e);
                msg.show(this);
            } catch (BasicException e) {
                MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotloadreportdata"), e);
                msg.show(this);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        m_App.waitCursorEnd();
    }
}
