<%@tag description="Test Select Form tag" pageEncoding="UTF-8"%>
<h1 class="content-subhead">Processing Unit Scheduling Simulator
	for Computers</h1>
<form method="POST" action="Scheduler" enctype="multipart/form-data"
	class="upload-form pure-form pure-form-stacked">
	<div class="file-upload-container pure-input-1">
		<div class="file-upload-override-button left pure-button">
			<input class="file-upload-button" type="file" name="file"
				id="file-upload-button" required /> Choose test file
		</div>
		<div class="file-upload-filename left" id="file-upload-filename"
			contenteditable="false">No file selected</div>
	</div>
	<input class="pure-input-1-1" name="quantum" placeholder="Quantum"
		type="text" min="1" step="1" required> <br /> <input
		class="pure-button custom-button" type="submit" value="Upload"
		name="upload" id="upload" />
</form>