package org.cgiar.ccafs.ap.data.model;
// Generated Mar 1, 2016 11:52:05 AM by Hibernate Tools 4.3.1.Final


/**
 * OutcomeSynthesis generated by hbm2java
 */
public class OutcomeSynthesis implements java.io.Serializable {


  private Integer id;
  private int ipProgamId;
  private IPProgram ipprogram;

  private int year;


  private int midOutcomeId;

  private int indicadorId;
  private Float achieved;
  private String achievedText;
  private String synthesisAnual;
  private String synthesisGender;

  private String discrepancy;


  private Double achievedExpected;
  private String achievedExpectedText;

  public OutcomeSynthesis() {
  }


  public OutcomeSynthesis(int ipProgamId, int year, int midOutcomeId, int indicadorId) {
    this.ipProgamId = ipProgamId;
    this.year = year;
    this.midOutcomeId = midOutcomeId;
    this.indicadorId = indicadorId;
  }

  public OutcomeSynthesis(int ipProgamId, int year, int midOutcomeId, int indicadorId, Float achieved,
    String synthesisAnual, String synthesisGender, String discrepancy) {
    this.ipProgamId = ipProgamId;
    this.year = year;
    this.midOutcomeId = midOutcomeId;
    this.indicadorId = indicadorId;
    this.achieved = achieved;
    this.synthesisAnual = synthesisAnual;
    this.synthesisGender = synthesisGender;
    this.discrepancy = discrepancy;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    OutcomeSynthesis other = (OutcomeSynthesis) obj;
    if (indicadorId != other.indicadorId) {
      return false;
    }
    if (ipProgamId != other.ipProgamId) {
      return false;
    }
    if (midOutcomeId != other.midOutcomeId) {
      return false;
    }
    return true;
  }

  public Float getAchieved() {
    return this.achieved;
  }

  public Double getAchievedExpected() {
    return achievedExpected;
  }


  public String getAchievedExpectedText() {
    return achievedExpectedText;
  }


  public String getAchievedText() {
    return achievedText;
  }


  public String getDiscrepancy() {
    return this.discrepancy;
  }


  public Integer getId() {
    return this.id;
  }

  public int getIndicadorId() {
    return this.indicadorId;
  }


  public int getIpProgamId() {
    return this.ipProgamId;
  }


  public IPProgram getIpprogram() {
    return ipprogram;
  }

  public int getMidOutcomeId() {
    return this.midOutcomeId;
  }

  public String getSynthesisAnual() {
    return this.synthesisAnual;
  }

  public String getSynthesisGender() {
    return this.synthesisGender;
  }

  public int getYear() {
    return this.year;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + indicadorId;
    result = prime * result + ipProgamId;
    result = prime * result + midOutcomeId;
    return result;
  }

  public void setAchieved(Float achieved) {
    this.achieved = achieved;
  }

  public void setAchievedExpected(Double achievedExpected) {
    this.achievedExpected = achievedExpected;
  }

  public void setAchievedExpectedText(String achievedExpectedText) {
    this.achievedExpectedText = achievedExpectedText;
  }

  public void setAchievedText(String achievedText) {
    this.achievedText = achievedText;
  }

  public void setDiscrepancy(String discrepancy) {
    this.discrepancy = discrepancy;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setIndicadorId(int indicadorId) {
    this.indicadorId = indicadorId;
  }

  public void setIpProgamId(int ipProgamId) {
    this.ipProgamId = ipProgamId;
  }

  public void setIpprogram(IPProgram ipprogram) {
    this.ipprogram = ipprogram;
  }

  public void setMidOutcomeId(int midOutcomeId) {
    this.midOutcomeId = midOutcomeId;
  }

  public void setSynthesisAnual(String synthesisAnual) {
    this.synthesisAnual = synthesisAnual;
  }

  public void setSynthesisGender(String synthesisGender) {
    this.synthesisGender = synthesisGender;
  }

  public void setYear(int year) {
    this.year = year;
  }


}

