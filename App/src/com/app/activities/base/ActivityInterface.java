package com.app.activities.base;


public interface ActivityInterface {
	/**
	 * Implement this in your activity to add functionality on home button tap.
	 * Need to be used along with isHomeButtonEnabled()
	 */
	public void onHomeButtonTapped();

	/**
	 * Return true to enable home button and false to disable
	 */
	public boolean isHomeButtonEnabled();

	/**
	 * Return true to handle push notification and false to disable
	 */
	public boolean isHandlingPushNotification();

	/**
	 * Implement this function to update any user profile related fields in the
	 * activity. This is the method that is called in the activity when user
	 * profile data is updated using sync service.
	 */
	public void updateUserProfileUIFields();

	/**
	 * Return true is you want profile sync request to happen on activity start
	 */
	public boolean isProfileSyncOnResumeEnabled();

	public void onLinkClicked(String type, String id);

	public void onVideoClicked(String videoId);
}
