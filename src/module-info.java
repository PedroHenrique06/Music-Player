module MusicAppLpII {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.media;
	requires javafx.graphics;
	

	exports br.ufrn.imd.controller to javafx.fxml;
	opens application to javafx.graphics, javafx.fxml;
	opens br.ufrn.imd.controller to javafx.fxml;
	
}
