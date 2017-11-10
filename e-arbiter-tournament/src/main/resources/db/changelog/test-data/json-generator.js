// it is a retarded code with "fake" functions which are called by www.json-generator.com
// be careful with dates, sometimes enddate for finished tournament may be weird... also, start date for draft should be null
// also, generate separately quiz tasks and code tasks, {{repeat}} doesnt work well in this case
// check correct flag for quiz task, sometims there is only 'falses'

// insert without 'var gen =' line, its only for preventing IJ showing errors
var gen =
    {
        'ownerId': '{{integer(1, 8)}}',
        'name': '{{lorem(6, "words")}}',
        'description': '{{lorem(3)}}',
        'startDate': '{{date(new Date(2017, 4, 1), new Date(2017, 6, 1), "YYYY-MM-ddThh:mm:ss")}}', // specify it for your needs, null for draft
        // 'startDate': null,
        'endDate': '{{date(new Date(2017, 6, 2), new Date(2017, 8, 1), "YYYY-MM-ddThh:mm:ss")}}',
        // 'endDate': '{{date(new Date(2017, 11, 2), new Date(2017, 12, 1), "YYYY-MM-ddThh:mm:ss")}}', // specify it for your needs
        'publicFlag': '{{bool()}}',
        'resultsVisibleForJoinedUsers': '{{bool()}}',
        'status': 'FINISHED', // specify it for your needs
        'password': function () {
            if (!this.publicFlag) return "password" + Math.floor(Math.random() * (89324 - 1 + 1)) + 1;
            return null;
        },
        'joinedUsersIds': function () {
            if (this.status === "DRAFT") return [];
            var min = 2;
            var max = 10;
            var users = Math.floor(Math.random() * (max - min + 1)) + min;

            var usersList = [];

            for (var i = 0; i < users; i++) {
                var userId = this.ownerId;

                while (usersList.indexOf(userId) !== -1 || userId === this.ownerId) {
                    userId = Math.floor(Math.random() * (20 - 1 + 1)) + 1;
                }
                usersList.push(userId);
            }

            return usersList;
        },
        'blockedUsersIds': [],
        'tasks': [
            '{{repeat(0, 10)}}', // code task first
            {
                'type': 'CodeTask',
                'name': '{{lorem(5, "words")}}',
                'description': '{{lorem(2)}}',
                'codeTaskTestSets': [
                    '{{repeat(1,6)}}',
                    {
                        'expectedResult': '' + '{{integer(50, 300)}}',
                        'parameters': [
                            '{{repeat(1,5)}}',
                            '{{integer(-30, 30)}}'
                        ]
                    }
                ],
                'languages': function () {
                    var amount = Math.floor(Math.random() * (4 - 1 + 1)) + 1;
                    var languages = ["CPP", "C11", "JAVA", "PYTHON"];
                    var languagesToReturn = [];
                    for (var i = 0; i < amount; i++) {
                        var currentLanguageToAdd = Math.floor(Math.random() * (4 - 1 + 1)) + 0;
                        if (languagesToReturn.indexOf(languages[currentLanguageToAdd]) === -1) {
                            languagesToReturn.push(languages[currentLanguageToAdd]);
                        }
                    }
                    return languagesToReturn;
                },
                'timeoutInMs': '{{random(50, 5000)}}'
            }
        ]
    }

// generate it separately and add by yourself to task array,
// 'var quizTasks =' not needed, array start does but insert to existing array without array signs later
// this is only for 'repeat' function
var quizTasks =
    [
        '{{repeat(0, 10)}}', // quiz tasks now
        {
            'type': 'QuizTask',
            'name': '{{lorem(5, "words")}}',
            'description': '{{lorem(2)}}',
            'maxAttempts': '{{integer(1, 3)}}',
            'questions': [
                '{{repeat(1, 5)}}',
                {
                    'content': '{{lorem(20, "words")}}',
                    'answers': [
                        '{{repeat(2, 5)}}',
                        {
                            'content': '{{lorem(5, "words")}}',
                            'correct': '{{bool()}}'
                        }
                    ]
                }
            ]
        }
    ]