/**
 * 
 */

/*$(document).ready(function() {
	function toggleDarkMode() {
		$('body').toggleClass('light-mode');
		var isDarkMode = $('body').hasClass('light-mode');
		localStorage.setItem('light-mode', isDarkMode);
	}

	$('#MKSOLchangemode').click(function() {
		toggleDarkMode();
	});

	var darkModeEnabled = localStorage.getItem('light-mode');
	if (darkModeEnabled === 'true') {
		$('body').addClass('light-mode');
	}
});
*/

$(document).ready(function() {
    function toggleDarkMode() {
        $('*').toggleClass('light-mode');
        
        var isDarkMode = $('*').hasClass('light-mode');
        localStorage.setItem('light-mode', isDarkMode);
    }

    $('#MKSOLchangemode').click(function() {
        toggleDarkMode();
    });

    var darkModeEnabled = localStorage.getItem('light-mode');
    if (darkModeEnabled === 'true') {
        $('*').addClass('light-mode');
    }
});
