import * as functions from 'firebase-functions';
import * as admin from 'firebase-admin';

// // Start writing Firebase Functions
// // https://firebase.google.com/docs/functions/typescript
//
// export const helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

admin.initializeApp();

export const countTotalInstallProfilesOnNew =
    functions.firestore
        .document('installProfiles')
        .onCreate((change, context) => {

            const allInstallProfileCollectionRef = admin.firestore().collection('installProfiles/{installID}');
            const firestoreStatsDocRef = admin.firestore().collection('firestoreStats').doc('stats');

            allInstallProfileCollectionRef.get().then(snap => {

                const count = snap.size;
                firestoreStatsDocRef.update({ totalInstallProfilesCount: count });
                return;

            });

        })

export const calcPercentileWrite =
    functions.firestore
    .document('installProfiles/{installID}')
    .onWrite((change, context) => {

        const updatedInstallProfile = change.after.data();
        const installID = updatedInstallProfile.installID
        const updatedIPaverage = updatedInstallProfile.average;
        const percentileCountInstallprofiles = 0;

        const firestoreStatsRef = admin.firestore().collection('firestoreStats').doc('stats');
        const firstpromise = firestoreStatsRef.get().then(doc => {
            return doc.data().totalInstallProfilesCount;
        });

        const belowGivenAverageInstallProfileCollectionRef = admin.firestore().collection('installProfiles/' + installID).where("average", "<", updatedIPaverage);
        const secondpromise = firstpromise
            .then(eh => { return belowGivenAverageInstallProfileCollectionRef.get().then(snap => { return snap.size; }) })

        return admin.firestore().collection('installProfiles/' + installID).doc('betterThanInstallProfilesCount').update(secondpromise);

    });

