document.addEventListener('DOMContentLoaded', function() {
    var container = document.getElementById('okr-table');
    var hot;

    $('#jstree').jstree();

    var hotSettings = {
        data: [],
        colHeaders: ['No', 'Period', 'startDate', 'endDate', 'currentPeriod'],
        columns: [
            { data: 'No' },
            { data: 'name' },
            { data: 'startDate' },
            { data: 'endDate' },
            { data: 'currentPeriod' }
        ],
        stretchH: 'all',
        autoWrapRow: true,
        height: 400,
        readOnly: true,
        licenseKey: 'non-commercial-and-evaluation'
    };

    hot = new Handsontable(container, hotSettings);

    fetch('period/loaddata')
        .then(response => response.json())
        .then(jsonData => {
            var data = [];
            var counter = 1;

            if (jsonData.data && Array.isArray(jsonData.data)) {
                for (var i = 0; i < jsonData.data.length; i++) {
                    var item = jsonData.data[i];
                    if (item.childs && Array.isArray(item.childs)) {
                        for (var j = 0; j < item.childs.length; j++) {
                            var childItem = item.childs[j];
                            var childData = {
                                No: counter++,
                                name: childItem.name,
                                startDate: childItem.startDate,
                                endDate: childItem.endDate,
                                currentPeriod: childItem.currentPeriod
                            };
                            data.push(childData);
                        }
                    }
                }
            } else {
                console.error('Unexpected data structure in the JSON data');
            }

            hot.loadData(data);
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
});