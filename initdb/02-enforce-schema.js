const ARTWORKS_MOMA_SCHEMA = {
  $jsonSchema: {
    bsonType: 'object',
    required: [
      'Artist',
      'Department',
      'ObjectID',
      'Title',
      'URL'
    ],
    additionalProperties: true,
    properties: {
      _id: {
        bsonType: 'objectId'
      },
      AccessionNumber: {
        bsonType: 'string'
      },
      Artist: {
        bsonType: 'array',
        items: {
          bsonType: 'string'
        }
      },
      ArtistBio: {
        bsonType: 'array',
        items: {
          bsonType: 'string'
        }
      },
      BeginDate: {
        bsonType: 'array',
        items: {
          bsonType: 'int'
        }
      },
      Cataloged: {
        bsonType: 'string'
      },
      Classification: {
        bsonType: 'string'
      },
      ConstituentID: {
        bsonType: 'array',
        items: {
          bsonType: 'int'
        }
      },
      CreditLine: {
        bsonType: [
          'string',
          'null'
        ]
      },
      Date: {
        bsonType: [
          'string',
          'null'
        ]
      },
      DateAcquired: {
        bsonType: [
          'string',
          'null'
        ]
      },
      Department: {
        bsonType: 'string'
      },
      'Depth (cm)': {
        bsonType: [
          'int',
          'double'
        ]
      },
      'Diameter (cm)': {
        bsonType: 'double'
      },
      Dimensions: {
        bsonType: [
          'string',
          'null'
        ]
      },
      'Duration (sec.)': {
        bsonType: 'int'
      },
      EndDate: {
        bsonType: 'array',
        items: {
          bsonType: 'int'
        }
      },
      Gender: {
        bsonType: 'array',
        items: {
          bsonType: 'string'
        }
      },
      'Height (cm)': {
        bsonType: [
          'double',
          'int'
        ]
      },
      ImageURL: {
        bsonType: [
          'string',
          'null'
        ]
      },
      'Length (cm)': {
        bsonType: 'double'
      },
      Medium: {
        bsonType: [
          'string',
          'null'
        ]
      },
      Nationality: {
        bsonType: 'array',
        items: {
          bsonType: 'string'
        }
      },
      ObjectID: {
        bsonType: 'int'
      },
      OnView: {
        bsonType: 'string'
      },
      Title: {
        bsonType: 'string'
      },
      URL: {
        bsonType: [
          'string',
          'null'
        ]
      },
      'Weight (kg)': {
        bsonType: 'double'
      },
      'Width (cm)': {
        bsonType: [
          'double',
          'int'
        ]
      }
    }
  }
};

use('artworks');

db.runCommand({
  collMod: "moma_embedded",
  validator: ARTWORKS_MOMA_SCHEMA,
  validationLevel: "strict",  // Options: "off", "moderate", "strict"
  validationAction: "error"   // Options: "warn", "error"
});
