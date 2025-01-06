package com.Demo.journalApplication.api.response;

public class weatherResponse {
	private Main main;

	public Main getMain() {
		return main;
	}
	public void setMain(Main main) {
		this.main = main;
	}
	public class Main {
		private Double temp;

		public Double getTemp() {
			return temp;
		}

	}
}