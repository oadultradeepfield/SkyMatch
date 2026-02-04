# SkyMatch Backend API Specification

## Search Constellations

### `GET /api/constellations`

**Query Parameters:**

| Parameter | Type   | Required | Description                                        |
|-----------|--------|----------|----------------------------------------------------|
| `query`   | string | No       | Search term (case-insensitive). Empty returns all. |

**Response:** `200 OK`

```json
{
  "constellations": [
    {
      "latinName": "Orion",
      "englishName": "The Hunter",
      "imageUrl": "https://example.com/orion.jpg"
    }
  ]
}
```

## Submit Image for Solving

### `POST /api/solve`

**Request:** `multipart/form-data`

| Field   | Type | Required | Description           |
|---------|------|----------|-----------------------|
| `image` | file | Yes      | Image file (JPEG/PNG) |

**Response:** `202 Accepted`

```json
{
  "jobId": "550e8400-e29b-41d4-a716-446655440000"
}
```

## Get Solving Status

### `GET /api/solve/{jobId}`

**Response:** `200 OK`

```json
{
  "jobId": "550e8400-e29b-41d4-a716-446655440000",
  "status": "SUCCESS",
  "annotatedImageUrl": "https://example.com/annotated/550e8400.jpg",
  "identifiedObjects": [
    {
      "type": "STAR",
      "identifier": "HIP 27989",
      "name": "Betelgeuse",
      "constellation": {
        "latinName": "Orion",
        "englishName": "The Hunter"
      },
      "xCoordinate": 412.6,
      "yCoordinate": 238.1,
      "starDetails": {
        "visualMagnitude": 0.42,
        "spectralType": "M",
        "distanceParsecs": 152.0
      }
    },
    {
      "type": "DEEP_SKY_OBJECT",
      "identifier": "M31",
      "name": "Andromeda Galaxy",
      "constellation": {
        "latinName": "Andromeda",
        "englishName": "The Chained Maiden"
      },
      "xCoordinate": 120.0,
      "yCoordinate": 88.0,
      "deepSkyDetails": {
        "objectType": "GALAXY"
      }
    }
  ],
  "createdAt": "2026-02-03T10:30:00Z"
}
```

**Status Values:** `QUEUED` | `IDENTIFYING_OBJECTS` | `GETTING_MORE_DETAILS` | `SUCCESS` |
`FAILURE` | `CANCELLED`

## Cancel Solving Job

### `DELETE /api/solve/{jobId}`

**Response:** `200 OK`

```json
{
  "jobId": "550e8400-e29b-41d4-a716-446655440000",
  "status": "CANCELLED"
}
```
