package tv.fun.appassisttest.common;

public class PhoneCommon extends Common{
	
	public void Home(int iTimes){
		for(int i = 0; i < iTimes; i++){
			Home();
		}
	}
	
	public void Menu(int iTimes){
		for(int i = 0; i < iTimes; i++){
			Menu();
		}
	}
	
	public void Back(int iTimes){
		for(int i = 0; i < iTimes; i++){
			Back();
		}
	}
	
	public void Click(int x, int y, int iTimes){
		for(int i = 0; i < iTimes; i++){
			Click(x, y);
		}
	}
	
	public boolean SwipeL2R(int x, int y, int iSwipeLen, int iSteps, int iTimes){
		for(int i = 0; i < iTimes; i++){
			SwipeL2R(x, y, iSwipeLen, iSteps);
		}
		return true;
	}
	
	public boolean SwipeR2L(int x, int y, int iSwipeLen, int iSteps, int iTimes){
		for(int i = 0; i < iTimes; i++){
			SwipeR2L(x, y, iSwipeLen, iSteps);
		}
		return true;
	}
	
	public boolean SwipeT2B(int x, int y, int iSwipeLen, int iSteps, int iTimes){
		for(int i = 0; i < iTimes; i++){
			SwipeT2B(x, y, iSwipeLen, iSteps);
		}
		return true;
	}
	
	public boolean SwipeB2T(int x, int y, int iSwipeLen, int iSteps, int iTimes){
		for(int i = 0; i < iTimes; i++){
			SwipeB2T(x, y, iSwipeLen, iSteps);
		}
		return true;
	}
	
//	// no effect
//	public void ClickFast(int x, int y, int iTimes){
//		long timeout = Configurator.getInstance().getActionAcknowledgmentTimeout();
//		Configurator.getInstance().setActionAcknowledgmentTimeout(500);
//		for(int i = 0; i < iTimes; i++){
//			Click(x, y);
//		}
//		Configurator.getInstance().setActionAcknowledgmentTimeout(timeout);
//	}
	
	public void PressKey(int keyCode, int iTimes){
		for(int i = 0; i < iTimes; i++){
			PressKey(keyCode);
		}
	}
}