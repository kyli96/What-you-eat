/**
 * @author kyli96@gmail.com
 */

var foodjournal = foodjournal || {};

$(document).ready(function() {
    foodjournal.init();
});

foodjournal.init = function() {
    foodjournal.uploader = new qq.FileUploader({
        element: $('#uploadButton')[0],
        action: "/ws/picture",
        template: '<div class="qq-uploader">' +
            '<div class="qq-upload-drop-area"><span>Drop files here to upload</span></div>' +
            '<div class="qq-upload-button">Add Picture</div>' +
            '<ul class="qq-upload-list"></ul>' +
            '</div>',
        onComplete: function(id, file, response) {
            if (response && response.success) {
                $('#pictureId').val(response.id);
            } else {
                alert(response.message);
            }
        }
    });
}
