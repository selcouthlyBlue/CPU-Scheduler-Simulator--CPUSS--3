$(document).ready(function() {
	
	/* ------------------------
	 * File upload form scripts
	 * ------------------------
	 */
	
	$("#file-upload-button").change(function () {
		var fileName = $(this).val().replace('C:\\fakepath\\', '');
		$("#file-upload-filename").html(fileName);
	});
});