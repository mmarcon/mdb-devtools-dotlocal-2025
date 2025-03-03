import { OllamaEmbeddings } from '@langchain/ollama';
import fs from 'fs';
import sjson from 'stream-json';
import sarray from 'stream-json/streamers/StreamArray.js';
const { parser } = sjson;
const { streamArray } = sarray;
import { Transform } from 'stream';
import { MongoClient } from 'mongodb';

const data = [];

const embeddings = new OllamaEmbeddings({
  model: 'mxbai-embed-large',
  baseUrl: 'http://localhost:11434'
});

const client = new MongoClient('mongodb://localhost:27317?directConnection=true');
await client.connect();
const db = client.db('artworks');
const collection = db.collection('moma_embedded');

const fileStream = fs.createReadStream('../initdb/data/Artworks.json');
const jsonStream = fileStream.pipe(parser()).pipe(streamArray());

const processItem = async (item) => {
  if (item.value.Title) {
    try {
      const embedding = await embeddings.embedQuery(item.value.Title);
      item.value.EmbeddedTitle = embedding;
      data.push(item.value);
      await collection.insertOne(item.value);
    } catch (err) {
      console.error('Error generating embedding:', err);
    }
  }
};

const transformStream = new Transform({
  objectMode: true,
  transform: (item, encoding, callback) => {
    processItem(item).then(() => callback()).catch(callback);
  }
});

jsonStream.pipe(transformStream).on('finish', () => {
  console.log('Finished processing file');
  fs.writeFileSync('../initdb/data/Artworks-embedded.json', JSON.stringify(data, null, 2));
});
