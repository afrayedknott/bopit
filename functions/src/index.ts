import * as functions from 'firebase-functions';

// // Start writing Firebase Functions
// // https://firebase.google.com/docs/functions/typescript
//
// export const helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

export const calcPercentile = functions.firestore
    .document('games')
    .onUpdate((change, context) => {

        const newValue = change.after.data();
        const name = newValue.name;

    });