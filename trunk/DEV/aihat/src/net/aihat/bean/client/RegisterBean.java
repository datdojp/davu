package net.aihat.bean.client;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;

import net.aihat.utils.AihatConstants;
import net.aihat.utils.AihatUtils;
import net.aihat.utils.BeanUtils;

import org.apache.log4j.Logger;

public class RegisterBean extends BaseClientBean {
	public RegisterBean() {
		logger = Logger.getLogger(RegisterBean.class);
		cleanAllFields();
	}
	
	/**
	 * OVERRIDE
	 */
	protected List getCurrentDtoList() {
		return null;
	}
	public String getBeanName() {
		return "registerBean";
	}
	public String init() {
		super.init();
		cleanAllFields();
		return null;
	}
	public synchronized void initAjax(AjaxBehaviorEvent e) {
		try {
			init();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	/**
	 * CONFIRM QUESTION
	 */
	private static final String[] QUESTIONS = {
		"CQ001", "CQ002", "CQ003", "CQ004", "CQ005", "CQ006", "CQ007", "CQ008", "CQ009", "CQ010"
	};
	private ConfirmQuestion generateConfirmQuestion() {
		int idx = (int) Math.floor(QUESTIONS.length * Math.random());
		return new ConfirmQuestion(
						BeanUtils.getBundleMsg(QUESTIONS[idx]),
						BeanUtils.getBundleMsg(QUESTIONS[idx] + "-A"));
	}
	public class ConfirmQuestion {
		private String question;
		private List<String> answers = new ArrayList<String>();
		
		public ConfirmQuestion() {}
		public ConfirmQuestion(String question, String answerString) {
			this.question = question;
			if(!AihatUtils.isEmpty(answerString)) {
				String[] splitted = answerString.split("#");
				answers = new ArrayList<String>();
				for(String anAnswer : splitted) {
					answers.add(anAnswer);
				}
			}
		}
		
		//getter setter
		public String getQuestion() {
			return question;
		}
		public void setQuestion(String question) {
			this.question = question;
		}
		public List<String> getAnswers() {
			return answers;
		}
		public void setAnswers(List<String> answers) {
			this.answers = answers;
		}
	}
	/**
	 * END OF CONFIRM QUESTION
	 */
	private ConfirmQuestion confirmQuestion;
	private String mail;
	private String password;
	private String confirmPassword;
	private String answer;
	private boolean confirmPolicy = false;
	private boolean mailAvailable = false;
	private String name;
	private String sex;
	private int birthdayDD;
	private int birthdayMM;
	private int birthdayYY;
	
	public synchronized void register(AjaxBehaviorEvent e) {
		try {
			//check name
			if(AihatUtils.isEmpty(name)) {
				addErrorMessage(BeanUtils.getBundleMsg("CM0021"));
				return;
			}

			//check birthday
			String yyyymmdd = "" +
					birthdayYY + 
					(birthdayMM < 10 ? "0"+birthdayMM : birthdayMM) +
					(birthdayDD < 10 ? "0"+birthdayDD : birthdayDD);
			Date birthday = null;
			try {
				birthday = AihatConstants.SDF_YYYYMMDD.parse(yyyymmdd);
			} catch (ParseException ex) {
				addErrorMessage(BeanUtils.getBundleMsg("CM0022"));
				return;
			}
			
			//check mail
			if(AihatUtils.isEmpty(mail)) {
				addErrorMessage(BeanUtils.getBundleMsg("CM0013"));
				return;
			}
			checkMailAvailability(e);
			if(!mailAvailable) {
				addErrorMessage(BeanUtils.getBundleMsg("CM0014"));
				return;
			}
			
			//check password & confirm password
			if(AihatUtils.isEmpty(password) || AihatUtils.isEmpty(confirmPassword)) {
				addErrorMessage(BeanUtils.getBundleMsg("CM0015"));
				return;
			}
			if(!AihatUtils.checkPasswordSafeEnough(password)) {
				addErrorMessage(BeanUtils.getBundleMsg("CM0004"));
				return;
			}
			if(!password.equals(confirmPassword)) {
				addErrorMessage(BeanUtils.getBundleMsg("CM0003"));
				return;
			}
			
			//check confirm question
			if(AihatUtils.isEmpty(answer)) {
				addErrorMessage(BeanUtils.getBundleMsg("CM0016"));
				return;
			}
			if(confirmQuestion.getAnswers().indexOf(answer) < 0) {
				addErrorMessage(BeanUtils.getBundleMsg("CM0017"));
				return;
			}
			
			//check confirm policy
			if(!confirmPolicy) {
				addErrorMessage(BeanUtils.getBundleMsg("CM0019"));
				return;
			}
			
			//all okie, save the new user
			getUserService().registerNewUser(mail, password, name, sex, birthday);
			addInfoMessage(BeanUtils.getBundleMsg("CM0018"));
			
			//clear all fields
			cleanAllFields();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	protected void cleanAllFields() {
		super.cleanAllFields();
		name = null;
		sex = "U";
		birthdayDD = 1;
		birthdayMM = 1;
		birthdayYY = 1986;
		confirmQuestion = generateConfirmQuestion();
		mail = null;
		password = null;
		confirmPassword = null;
		answer = null;
		confirmPolicy = false;
		mailAvailable = false;
	}
	
	public synchronized void changeConfirmQuestion(AjaxBehaviorEvent e) {
		try {
			confirmQuestion = generateConfirmQuestion();
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	public synchronized void checkMailAvailability(AjaxBehaviorEvent e) {
		try {
			if(AihatUtils.isEmpty(mail)) {
				return;
			}
	
			long n = getSearchService().searchUsers(null, mail, null, null, null, null, false, null).getnResults();
			mailAvailable = n==0;
		} catch (Throwable err) {
			handleGeneralError(err);
		}
	}
	
	//getter setter
	public ConfirmQuestion getConfirmQuestion() {
		return confirmQuestion;
	}
	public void setConfirmQuestion(ConfirmQuestion confirmQuestion) {
		this.confirmQuestion = confirmQuestion;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public boolean isConfirmPolicy() {
		return confirmPolicy;
	}
	public void setConfirmPolicy(boolean confirmPolicy) {
		this.confirmPolicy = confirmPolicy;
	}
	public boolean isMailAvailable() {
		return mailAvailable;
	}
	public void setMailAvailable(boolean mailAvailable) {
		this.mailAvailable = mailAvailable;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getBirthdayDD() {
		return birthdayDD;
	}

	public void setBirthdayDD(int birthdayDD) {
		this.birthdayDD = birthdayDD;
	}

	public int getBirthdayMM() {
		return birthdayMM;
	}

	public void setBirthdayMM(int birthdayMM) {
		this.birthdayMM = birthdayMM;
	}

	public int getBirthdayYY() {
		return birthdayYY;
	}

	public void setBirthdayYY(int birthdayYY) {
		this.birthdayYY = birthdayYY;
	}
}
